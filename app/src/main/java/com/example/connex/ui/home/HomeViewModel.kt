package com.example.connex.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.syncCallLog
import com.example.domain.model.ApiState
import com.example.domain.model.response.asDomain
import com.example.domain.usecase.ReadMostCallUsersUseCase
import com.example.domain.usecase.SyncCallLogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val readMostCallUsersUseCase: ReadMostCallUsersUseCase,
    val syncCallLogsUseCase: SyncCallLogsUseCase,
    @ApplicationContext private val applicationContext: Context
): ViewModel() {
    var userId: Long = 111111

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchSyncCallLogs()
        = syncCallLogsUseCase(userId, syncCallLog(applicationContext.contentResolver, 100).toList()).first()


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchReadMostCallUsers() {
        viewModelScope.launch {
            val test = fetchSyncCallLogs()
            if ( test is ApiState.Success) {
                Log.d("daeyoung", "fetchSyncCallLogs Success: ${test.data}")
                when (val result = readMostCallUsersUseCase(userId).first()) {
                    is ApiState.Error -> Log.d("daeyoung", "Error: ${result.errMsg}")
                    ApiState.Loading -> TODO()
                    is ApiState.NotResponse -> Log.d("daeyoung", "NotResponse: ${result.message},\n ${result.exception}")
                    is ApiState.Success -> {
                        val mostCalledDateTime = result.data.asDomain()
                        Log.d("daeyoung", "mostCalledDateTime: $mostCalledDateTime")
                    }
                }
            } else if (test is ApiState.Error){
                Log.d("daeyoung", "fetchSyncCallLogs fail: ${test.errMsg}")
            } else if (test is ApiState.NotResponse){
                Log.d("daeyoung", "fetchSyncCallLogs fail: ${test.message}")
            }
        }
    }
}