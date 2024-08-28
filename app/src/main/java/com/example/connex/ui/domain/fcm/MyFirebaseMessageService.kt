package com.example.connex.ui.domain.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.connex.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.connex.R
import com.example.connex.utils.Constants

class MyFirebaseMessageService : FirebaseMessagingService() {
    // 새로운 토큰이 생성될 때 마다 해당 콜백이 호출된다.
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("daeyoung", "onNewToken: $token")
        // 새로운 토큰 수신 시 서버로 전송
//        MainActivity.uploadToken(token)
    }

    // Foreground에서 Push Service를 받기 위해 Notification 설정
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("daeyoung", "From: " + remoteMessage!!.from)

        // Notification 메시지를 수신할 경우는
        // remoteMessage.notification?.body!! 여기에 내용이 저장되어있다.
        // Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)

        Log.d("daeyoung", "remoteMessage: ${remoteMessage.data}")
        Log.d("test", "onMessageReceived: ${remoteMessage}")

        if (remoteMessage.data["title"] != null && remoteMessage.data["body"] != null) {
            sendNotification(remoteMessage)
        } else {
            Log.i("daeyoung", "수신에러: 메시지를 수신하지 못했습니다. \n${remoteMessage.notification}")
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val title = remoteMessage.notification?.title ?: "Connex"
        val body = remoteMessage.notification?.body ?: "Test"
        val image = remoteMessage.data["profileImage"] ?: Constants.DEFAULT_PROFILE

        val initialPage = 1
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("${Constants.DEEP_LINK_URI}${Constants.NOTIFICATION_ROUTE}/${initialPage}"),
//            this,
//            MainActivity::class.java
        ).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
//            addNextIntentWithParentStack(deepLinkIntent)
//            getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
//        }

        val deepLinkPendingIntent = PendingIntent.getActivity(
            this,
            uniId,
            deepLinkIntent,
            flags
        )

        // 알림 채널 이름
//        val channelId = getString(R.string.firebase_notification_channel_id)
        val channelId = "channel1"

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.im_app_logo) // 아이콘 설정
//            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
//            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
            .setContentTitle(title) // 제목
            .setContentText(body) // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri) // 알림 소리
//            .setContentIntent(pendingIntent) // 알림 실행 시 Intent
            .setContentIntent(deepLinkPendingIntent) // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
        Log.d("daeyoung", "알림생성: " + notificationManager.toString())

    }

}