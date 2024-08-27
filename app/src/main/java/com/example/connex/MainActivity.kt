package com.example.connex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.util.Consumer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.connex.ui.domain.ManageBottomBarState
import com.example.connex.ui.domain.rememberApplicationState
import com.example.connex.ui.notification.Event
import com.example.connex.ui.notification.NotificationViewModel
import com.example.connex.ui.theme.ConnexTheme
import com.example.connex.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val notificationViewModel by viewModels<NotificationViewModel>()
    override fun onStart() {
        super.onStart()
        Log.d("test", "onStart, intent: $intent")
        intent?.data?.let { notificationViewModel.handleDeeplink(it) }
        // consume the deeplink
//        intent = null
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("test", "onNewIntent, intent: $intent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("daeyoung", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("daeyoung", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

        enableEdgeToEdge()
        setContent {
            Log.d("test", "onCreate(), setContent")
            Log.d("test", "onCreate(), intent: $intent")

            val appState = rememberApplicationState()
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()

            val event by notificationViewModel.event.collectAsStateWithLifecycle()

            LaunchedEffect(event) {
                Log.d("test", "LaunchedEffect, event: $event")
                when (val currentEvent = event) {
                    is Event.NavigateWithDeeplink -> appState.navigate(currentEvent.deeplink)
                    Event.None -> Unit
                }
                notificationViewModel.consumeEvent()
            }

            DisposableEffect(appState.navController) {
                val consumer = Consumer<Intent> { intent ->
                    Log.d("test", "DisposableEffect, intent: $intent")
                    intent.data?.let {
                        appState.navigate(it)
                    }
                }
                this@MainActivity.addOnNewIntentListener(consumer)
                onDispose {
                    this@MainActivity.removeOnNewIntentListener(consumer)
                }
            }



            ConnexTheme {
                ManageBottomBarState(navBackStackEntry, appState)
//                Log.d("daeyoung", "bottomState: ${appState.bottomBarState}")
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    RootNavhost(navBackStackEntry, appState)
                }
            }
        }
    }
}