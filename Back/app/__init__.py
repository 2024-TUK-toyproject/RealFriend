from fastapi import FastAPI

app = FastAPI(
    title = "Connex", 
    description="Connex API 문서\n어떤 api든 응답으로 토큰 만료가 뜨면 재로그인 필요",
    version="0.0.4"
    )

def create_app():
    from . import routes
    app.include_router(routes.router)

    return app

