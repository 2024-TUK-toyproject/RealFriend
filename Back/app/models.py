from sqlalchemy import Column, Integer, String, Boolean, Float, Double
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class user_info(Base):
    __tablename__ = 'user_info'

    user_id = Column(String(255), primary_key=True)
    name = Column(String(255))
    phone = Column(String(255))
    create_date = Column(String(255))
    last_login_date = Column(String(255))
    profile_image = Column(String(255))
    refresh_token = Column(String(255))

class temp_user_info(Base):
    __tablename__ = 'temp_user_info'

    num = Column(Integer, index = True,  primary_key=True)
    name = Column(String(255))
    phone = Column(String(255))
    create_date = Column(String(255))

class is_friend(Base):
    __tablename__ = 'is_friend'

    friend_id = Column(String(255), primary_key=True)
    from_user_id = Column(String(255))
    to_user_id = Column(String(255))
    create_date = Column(String(255))
    last_modified_date = Column(String(255))
    is_friend = Column(Boolean)
    shared_album_id = Column(String(255))

class phone_info(Base):
    __tablename__ = 'phone_info'

    phone_id = Column(Integer, index = True,primary_key=True)
    user_id = Column(String(255))
    name = Column(String(255))
    phone = Column(String(255))
    create_date = Column(String(255))
    last_modified_date = Column(String(255))

class call_record_info(Base):
    __tablename__ = 'call_record_info'

    call_id = Column(Integer, index = True, primary_key=True)
    user_id = Column(String(255))
    name = Column(String(255))
    phone = Column(String(255))
    date = Column(String(255))
    time = Column(String(255))
    duration = Column(Integer)
    type = Column(Integer)

class album_info(Base):
    __tablename__ = 'album_info'

    album_id = Column(String(255), primary_key=True)
    album_name = Column(String(255))
    create_user_id = Column(String(255))
    with_whom = Column(String(255))
    directory = Column(String(255))
