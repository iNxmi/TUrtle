from fastapi import APIRouter, HTTPException, Cookie, Response
from fastapi.responses import RedirectResponse
from pydantic import BaseModel
from datetime import datetime, timedelta, timezone
import jwt, os, hashlib

# Dummy DB -----------------------------------------
users = {
    "admin": hashlib.sha256("pass".encode()).hexdigest()
}
# Dummy DB -----------------------------------------

router = APIRouter()

SECRET_KEY = os.environ.get("JWT_SECRET")
EXPIRATION_TIME = int(os.environ.get("EXPIRATION_TIME", os.environ.get("EXPIRATION_TIME", 300)))

class LoginRequest(BaseModel):
    username: str
    password: str

@router.post("/api/login")
def login(data: LoginRequest, response: Response):
    expires = datetime.now(timezone.utc) + timedelta(seconds=EXPIRATION_TIME)
    hashed = hashlib.sha256(data.password.encode()).hexdigest()

    if data.username not in users:
        raise HTTPException(status_code=401, detail="USERNAME_DOES_NOT_EXIST")
    
    if users[data.username] == hashed:
        
        # payload can have arbatriary fields e.g. add role field: everytime we decode the jwt, we have access to those
        payload = {
            "username": data.username,
            "exp": expires # ,
            # "role": ...
        }

        token = jwt.encode(payload, SECRET_KEY, algorithm="HS256")
        
        response.set_cookie(
            key="session",
            value=token,
            httponly=True,
            max_age=EXPIRATION_TIME,
            expires=expires,
            samesite="Strict",
            secure=False
        )

        return {
                "message": "Login successful", 
                "token": token,
                "status": "LOGIN_SUCCESSFUL"
                }
        
    raise HTTPException(status_code=401, detail="INVALID_CREDENTIALS")

@router.post("/api/logout")
def logout():
    response = RedirectResponse(url="/dashboard", status_code=303)
    response.delete_cookie("session", path="/")
    return response


def get_current_user(token: str = Cookie(None)):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=["HS256"])
        return payload
    except jwt.ExpiredSignatureError:
        raise HTTPException(status_code=401, detail="Token expired")
    except jwt.InvalidTokenError:
        raise HTTPException(status_code=401, detail="Invalid token")
    