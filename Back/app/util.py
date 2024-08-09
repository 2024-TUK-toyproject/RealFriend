from .database import Database
from .config import Config
from fastapi import Depends, HTTPException
from fastapi.security import OAuth2PasswordBearer
from datetime import timedelta, datetime
from jose import JWTError, jwt
from pytz import timezone
from . import models

from .httpException import CustomException


class JWTService:

    def __init__(
        self,
        secret_key: str = Config.SECRET_KEY,
        algorithm: str = Config.ALGORITHM,
        access_token_expire_minutes: int = Config.ACCESS_TOKEN_EXPIRE_MINUTES,
        refresh_token_expire_minutes: int = Config.REFRESH_TOCKEN_EXPIRE_MINUTES,
        encoder = jwt.encode,
        decoder = jwt.decode
    ):
        self.secret_key = secret_key
        self.algorithm = algorithm
        self.access_token_expire_minutes = access_token_expire_minutes
        self.refresh_token_expire_minutes = refresh_token_expire_minutes
        self.encoder = encoder
        self.decoder = decoder

    def get_user(self, userName, key, session = Depends(Database().get_session)):
        try:
            userInfo = session.query(models.nok_info).filter_by(nok_name = userName, nok_key = key).first()

            if userInfo:
                return userInfo, "nok"
            else:
                userInfo = session.query(models.dementia_info).filter_by(dementia_name = userName, dementia_key = key).first()
            
                if userInfo:
                    return userInfo, "dementia"
                else:
                    return None
            
        except Exception as e:
            print(f"[ERROR] User information not found")
            raise CustomException(status_code=404, detail="User information not found")
    
        finally:
            session.close()

    def create_access_token(self, phone : str, key : str) -> str:
        return self._create_token(phone, key, self.access_token_expire_minutes)
    
    def create_refresh_token(self, phone: str, key : str) -> str:
        return self._create_token(phone, key, self.refresh_token_expire_minutes)
    
    def _create_token(self, phone : str, key : str, expires_delta: int) -> str:
        data = {
            "phone": phone,
            "key": key,
            "exp": datetime.utcnow() + timedelta(minutes = expires_delta)
        }
        return self.encoder(data, self.secret_key, self.algorithm)
    
    def check_token_expired(self, token: str) -> dict:
        payload = self.decoder(token, self.secret_key, self.algorithm)

        now = datetime.timestamp(datetime.now(timezone('Asia/Seoul')))
        if payload and payload["exp"] < now:
            return None

        return payload

    def get_current_user(self, token: str = Depends(OAuth2PasswordBearer(tokenUrl="/login")), session = Depends(Database().get_session)):
        try:

            if not self.check_token_expired(token):
                raise CustomException(
                    status_code=401,
                    detail="Expired token",
                    headers={"WWW-Authenticate": "Bearer"}
                )
            else:
                pass

            if session.query(models.refresh_token_info).filter_by(refresh_token = token).first():
                new_token = self.create_access_token(username, key)
            else:
                new_token = None

            payload = self.decoder(token, self.secret_key, self.algorithm)

            username: str = payload.get("name")
            key : str = payload.get("key")

            if username is None:
                raise CustomException(status_code=401, detail="asdasd")
            
            user, user_type = self.get_user(username, payload.get("key"), session)

            if user is None:
                raise CustomException(status_code=404, detail="User not found")
            
        # 에러 내용 전송 
        except JWTError as e:
            print(f"[ERROR] {e}")
            raise CustomException(status_code=401, detail=str(e), headers={"WWW-Authenticate": "Bearer"})
            
        return user, user_type, new_token