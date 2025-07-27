from fastapi import FastAPI, HTTPException, Depends
from fastapi.responses import FileResponse
import os
import hashlib
from pydantic import BaseModel

app = FastAPI()

@app.get("/api")
def api():
    return {"message": "Hallo von FastAPI"}

@app.get("/test/{item_id}")
async def read_item(item_id: str):
    return {"item_id": item_id}

# Dummy DB
users = {
    "admin": hashlib.sha256("password".encode()).hexdigest()
}

class LoginRequest(BaseModel):
    username: str
    password: str

@app.post("/login")
def login(data: LoginRequest):
    hashed = hashlib.sha256(data.password.encode()).hexdigest()
    if data.username in users and users[data.username] == hashed:
        return {"message": "Login successful", "token": "example-token"}
    raise HTTPException(status_code=401, detail="Invalid credentials")