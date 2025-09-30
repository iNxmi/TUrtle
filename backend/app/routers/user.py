from fastapi import APIRouter, Depends
from fastapi.responses import JSONResponse
from auth.login import get_current_user, users # users is the dummy db

router = APIRouter()

@router.post("/api/user")
def user(user = Depends(get_current_user)):
    return {"username": users[user["username"]]}