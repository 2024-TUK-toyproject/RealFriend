from pydantic import BaseModel, Field
from typing import List, Dict, Optional


# Response Model
class CommoneResponse(BaseModel):
    status : str = Field("success", example = "success")
    message : str = Field("성공메시지 or 오류메시지")


# Request Model
class Phone_list(BaseModel):
    