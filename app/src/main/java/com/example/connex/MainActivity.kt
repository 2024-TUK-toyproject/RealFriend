package com.example.connex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.connex.ui.component.util.addFocusCleaner
import com.example.connex.ui.domain.ManageBottomBarState
import com.example.connex.ui.domain.rememberApplicationState
import com.example.connex.ui.theme.ConnexTheme
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
            val appState = rememberApplicationState()
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
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
