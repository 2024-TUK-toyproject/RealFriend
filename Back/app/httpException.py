from fastapi import HTTPException, status, Depends
from fastapi.responses import JSONResponse


class CustomException(HTTPException):
    def __init__(self, status_code: int, detail: str):
        content = {"status": "error", "detail": detail}
        super().__init__(status_code=status_code, detail = content)


