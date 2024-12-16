package com.example.connex.ui.notification

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.entity.notification.FriendRequestInfo
import com.example.domain.usecase.friend.AcceptFriendRequestUseCase
import com.example.domain.usecase.friend.ReadAllFriendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

sealed interface Event {
    data class NavigateWithDeeplink(val deeplink: Uri) : Event
    object None : Event
}

@HiltViewModel
class NotificationViewModel @Inject constructor(
    val readAllFriendRequestUseCase: ReadAllFriendRequestUseCase,
    val acceptFriendRequestUseCase: AcceptFriendRequestUseCase,
) : ViewModel() {
    val event = MutableStateFlow<Event>(Event.None)
    private val _requestedFriend = MutableStateFlow<Map<String, List<FriendRequestInfo>>>(mapOf())
    val requestedFriend: StateFlow<Map<String, List<FriendRequestInfo>>> =
        _requestedFriend.asStateFlow()

    fun handleDeeplink(uri: Uri) {
        Log.d("test", "previous event: ${event.value}")
        event.update { Event.NavigateWithDeeplink(uri) }
        Log.d("test", "last event: ${event.value}")

    }

    fun consumeEvent() {
        event.update { Event.None }
    }


    suspend fun fetchReadAllFriendRequest(notResponse: () -> Unit) {
//        viewModelScope.launch {
        when (val result = readAllFriendRequestUseCase().first()) {
            is ApiState.Error -> TODO()
            ApiState.Loading -> TODO()
            is ApiState.NotResponse -> {
                Log.d("daeyoung", "message: ${result.message}\nexception: ${result.exception}")
                if (result.exception is ConnectException) {
                    notResponse()
                }
            }

            is ApiState.Success -> {
                _requestedFriend.update { result.data }
            }
        }
    }

    fun fetchAcceptFriendRequest(friendRequestId: Long, notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = acceptFriendRequestUseCase(friendRequestId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "message: ${result.message}\nexception: ${result.exception}")
                    if (result.exception is ConnectException) {
                        notResponse()
                    }
                }

                is ApiState.Success -> {
                    _requestedFriend.update { map ->
                        map.mapValues { (_, value) ->
                            value.toMutableList()
                                .apply { removeIf { it.friendRequestId == friendRequestId } }
                        }
                    }
                }
            }
        }
    }
}