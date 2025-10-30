from fastapi import APIRouter

router = APIRouter(prefix="/user")

@router.get
def get_all_users():
    returnm

@router.get("/{username}")
def get_user_by_username(username: str):
    return {"username": username}