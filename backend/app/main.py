from fastapi import Depends, FastAPI
from fastapi.middleware.cors import CORSMiddleware
from auth.login import get_current_user, router as login_router
from routers.user import router as user_router

app = FastAPI()


app.include_router(login_router)
app.include_router(user_router)




# ---------------------------------------
# just to see what's possible, should not be in this file
# ---------------------------------------
@app.get("/api")
def api():
    return {"message": "Hallo von FastAPI"}

@app.get("/api/test/{item_id}")
async def read_item(item_id: str, user = Depends(get_current_user)):
    return {"item_id": item_id}

