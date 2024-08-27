import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

from .config import Config

cred = credentials.Certificate(Config.cred_path)
firebase_admin.initialize_app(cred)


async def send_push_notification(token, data):

    message = messaging.Message(
        data = {
            'title': data["title"],
            'body': data["body"]
        },
        token=token
    )

    response = messaging.send(message)

    print('Successfully sent message:', response)

