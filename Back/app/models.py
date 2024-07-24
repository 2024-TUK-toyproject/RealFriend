from sqlalchemy import Column, Integer, String, Boolean, Float, Double
from sqlalchemy.ext.declarative import declarative_base


class user_info():
    __tablename__ = 'user_info'

    user_id = Column(String(255), primary_key=True)
    name = Column(String(255))
    phone = Column(String(255))
    create_date = Column(String(255))
    last_modified_date = Column(String(255))
    profile_image = Column(String(255))

class is_friend():
    __tablename__ = 'is_friend'

    friend_id = Column(String(255), primary_key=True)
    from_user_id = Column(String(255))
    to_user_id = Column(String(255))
    create_date = Column(String(255))
    last_modified_date = Column(String(255))
    is_friend = Column(Boolean)
    shared_album_id = Column(String(255))

class phone_info():
    __tablename__ = 'phone_info'

    phone_id = Column(String(255), primary_key=True)
    user_id = Column(String(255))
    name = Column(String(255))
    phone = Column(String(255))
    create_date = Column(String(255))
    last_modified_date = Column(String(255))
