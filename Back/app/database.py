from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from .config import Config

DB_URL = Config.db

class Database:
    def __init__(self):
        self.engine = create_engine(DB_URL, pool_recycle=500)
        self.SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=self.engine)

    def get_session(self):
        try:
            db_session = self.SessionLocal()
            yield db_session
        finally:
            db_session.close()

    def get_connection(self):
        conn = self.engine.connect()
        return conn
