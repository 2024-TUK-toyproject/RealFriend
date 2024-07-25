from fastapi import APIRouter, UploadFile, HTTPException, Depends
from fastapi.responses import JSONResponse



from .config import Config
from .schemes import *
from .service.upload import upload_service
from .service.download import download_service

import boto3
import uuid
import urllib

router = APIRouter()
s3 = boto3.client(
    "s3",
    aws_access_key_id=Config.s3_access_key,
    aws_secret_access_key=Config.s3_secret_key
)

@router.post("/test/upload")
async def upload(file: UploadFile, directory: str):
    try:
        filen_name = f"{str(uuid.uuid4())}.jpg"
        s3_key = f"{directory}/{filen_name}"

        s3.upload_fileobj(
            file.file,
            Config.s3_bucket,
            s3_key
        )

        url = "https://%s.s3.amazonaws.com/%s/%s" % (
            Config.s3_bucket,
            directory,
            urllib.parse.quote(filen_name)
        )
        return JSONResponse(content = "{'url': '%s'}" % url)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    

@router.post("/upload/phone", responses = {200 : {"model" : CommoneResponse, "description" : "동기화 성공"}, 400 : {"model" : CommoneResponse, "description" : "동기화 실패"}})
async def upload_phone_info(request : Phone_request, upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_phone_info(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.post("/upload/callrecord", responses = {200 : {"model" : CommoneResponse, "description" : "동기화 성공"}, 400 : {"model" : CommoneResponse, "description" : "동기화 실패"}})
async def upload_call_record(request : Call_record_request, upload_service : upload_service = Depends()):
    try:
        return await upload_service.upload_call_record(request)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))
    
@router.get("/get/{user_id}/lastcall", responses = {200 : {"model" : Last_call_response, "description" : "통화 기록 조회 성공"}, 400 : {"model" : CommoneResponse, "description" : "통화 기록 조회 실패"}})
async def get_last_call(user_id : str, download_service : download_service = Depends()):
    try:
        return await download_service.get_last_call(user_id)
    
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))