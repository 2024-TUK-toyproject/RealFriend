from fastapi import FastAPI

app = FastAPI(
    title = "Connex", 
    description="Connex API 문서",
    version="0.0.1"
    )

def create_app():
    from . import routes
    app.include_router(routes.router)

    return app

