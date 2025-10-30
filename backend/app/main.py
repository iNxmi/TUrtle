from fastapi import FastAPI
from starlette.middleware.cors import CORSMiddleware
from api.v1.router.user_router import router as user_router
from api.v1.router.auth_router import router as auth_router


def setup_cors(application: FastAPI):
    origins = [
        "http://localhost:3000",
        "http://127.0.0.1:3000",
        "http://0.0.0.0:3000"
    ]

    application.add_middleware(
        CORSMiddleware,
        allow_origins=origins,
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"]
    )


def setup_v1_routers(application: FastAPI):
    api_prefix = "/api/v1"

    application.include_router(user_router, prefix=api_prefix)
    application.include_router(auth_router, prefix=api_prefix)


app = FastAPI(title="TUrtleAPI")
setup_cors(app)
setup_v1_routers(app)


def get_database():
    db = database.
