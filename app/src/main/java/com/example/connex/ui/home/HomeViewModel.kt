package com.example.connex.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.syncCallLog
import com.example.data.datastore.TokenManager
import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledUsers
import com.example.domain.usecase.ReadMostCallUsersUseCase
import com.example.domain.usecase.SyncCallLogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val readMostCallUsersUseCase: ReadMostCallUsersUseCase,
    val syncCallLogsUseCase: SyncCallLogsUseCase,
    val tokenManager: TokenManager,
    @ApplicationContext private val applicationContext: Context,
) : ViewModel() {

    private val _mostCalledUsers = MutableStateFlow<ApiState<MostCalledUsers>>(ApiState.Loading)
    val mostCalledUsers: StateFlow<ApiState<MostCalledUsers>> = _mostCalledUsers.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchSyncCallLogs() {
        viewModelScope.launch {
            val collLogs = syncCallLog(tokenManager, applicationContext.contentResolver, 100)
            if (collLogs.isEmpty()) {
                fetchReadMostCallUsers()
            } else {
                when (val result = syncCallLogsUseCase(collLogs.toList()).first()) {
                    is ApiState.Error -> {
                        Log.d("daeYoung", "Error: ${result.errMsg}")
                    }
                    ApiState.Loading -> TODO()
                    is ApiState.NotResponse -> TODO()
                    is ApiState.Success -> {
                        fetchReadMostCallUsers()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchReadMostCallUsers() {
       _mostCalledUsers.update {
           readMostCallUsersUseCase().first()
       }
    }
}