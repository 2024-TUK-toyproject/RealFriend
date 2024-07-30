package com.example.connex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.connex.ui.initSettingGraph
import com.example.connex.ui.loginGraph
import com.example.connex.utils.Constants.INIT_SETTING_GRAPH
import com.example.connex.utils.Constants.LOGIN_GRAPH

@Composable
fun RootNavhost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LOGIN_GRAPH, modifier = Modifier.fillMaxSize()) {
        loginGraph(navController)
        initSettingGraph(navController)
    }
}