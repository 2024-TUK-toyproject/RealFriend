from fastapi import FastAPI

app = FastAPI()

def create_app():
    from . import routes
    app.include_router(routes.router)

    return app