from sqlalchemy.orm import Session
from app.api.v1.model.user_model import User
from typing import Optional


def get_user_by_id(database: Session, user_id: int) -> Optional[User]:
    return database.query(User).filter(User.id == user_id).first()


def get_user_by_username(database: Session, username: str) -> Optional[User]:
    return database.query(User).filter(User.username == username).first()


def get_user_by_email(database: Session, email: str) -> Optional[User]:
    return database.query(User).filter(User.email == email).first()


def create_user(database: Session, username: str, email: str, password: str) -> User:
    # TODO hash the password before inserting into database
    password_hash = password

    user = User(username=username, email=email, password_hash=password_hash)
    database.add(user)
    database.commit()
    database.refresh(user)
    return user


def delete_user(database: Session, user_id: int) -> bool:
    try:
        user = get_user_by_id(database, user_id)
        database.delete(user)
        database.commit()
        database.refresh(user)
        return True
    except:
        return False