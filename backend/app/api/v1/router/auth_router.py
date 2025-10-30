from fastapi import APIRouter, HTTPException, Cookie, Response
from fastapi.responses import RedirectResponse
from datetime import datetime, timedelta, timezone
from pydantic import BaseModel
import jwt, os, hashlib


class LoginRequest(BaseModel):
    email_or_username: str
    password: str


users = {
    "admin": hashlib.sha256("pass".encode()).hexdigest()
}

router = APIRouter(prefix="/auth")

JWT_SECRET_KEY = os.environ.get("JWT_SECRET")
JWT_EXPIRATION_TIME = int(os.environ.get("EXPIRATION_TIME", 300))


@router.post("/login")
def login(request: LoginRequest, response: Response):
    jwt_expiration = datetime.now(timezone.utc) + timedelta(seconds=JWT_EXPIRATION_TIME)
    password_hashed = hashlib.sha256(request.password.encode()).hexdigest()

    if request.email_or_username not in users:
        raise HTTPException(status_code=401, detail="USERNAME_DOES_NOT_EXIST")

    if users[request.email_or_username] != password_hashed:
        raise HTTPException(status_code=401, detail="INVALID_CREDENTIALS")

    payload = {
        "username": request.email_or_username,
        "jwt_expiration": jwt_expiration
    }

    jwt_token = jwt.encode(payload, JWT_SECRET_KEY, algorithm="HS256")

    response.set_cookie(
        key="session",
        value=jwt_token,
        httponly=True,
        max_age=JWT_EXPIRATION_TIME,
        expires=int(jwt_expiration.timestamp()),
        samesite="Strict",
        secure=False
    )

    return {
        "message": "Login successful",
        "jwt_token": jwt_token,
        "status": "LOGIN_SUCCESSFUL"
    }


@router.post("/logout")
def logout():
    response = RedirectResponse(url="/dashboard", status_code=303)
    response.delete_cookie("session", path="/")
    return response


def get_current_user(token: str = Cookie(None)):
    try:
        payload = jwt.decode(token, JWT_SECRET_KEY, algorithms=["HS256"])
        return payload
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Token expired")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="Invalid token")
