package com.example.connex.ui.home

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.model.response.Friend
import com.example.domain.model.response.asDomain
import com.example.domain.usecase.ReadAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


class FriendUiState(
    val friend: Friend,
    initialChecked: Boolean = false,
) {
    var isSelect by mutableStateOf(initialChecked)
}

data class FriendsRemoveUiState(
    val search: String = "",
    val userList: List<FriendUiState> = emptyList(),
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    val readAllFriendsUseCase: ReadAllFriendsUseCase,
) : ViewModel() {

    init {
        fetchReadAllFriends()
    }

    private val _friendsRemoveSearch = MutableStateFlow("")
    val friendsRemoveSearch: StateFlow<String> = _friendsRemoveSearch.asStateFlow()

    private val _friendsRemoveUserList = MutableStateFlow(emptyList<FriendUiState>())
    val friendsRemoveUserList: StateFlow<List<FriendUiState>> = _friendsRemoveUserList.asStateFlow()

    private val _filteredFriendsRemoveUserList = MutableStateFlow(emptyList<FriendUiState>())
    val filteredFriendsRemoveUserList: StateFlow<List<FriendUiState>> =
        _filteredFriendsRemoveUserList.asStateFlow()

    val count by derivedStateOf {
        friendsRemoveUserList.value.count()
    }


    val friendsRemoveUiState =
        combine(_friendsRemoveSearch, _filteredFriendsRemoveUserList) { search, userList ->
            FriendsRemoveUiState(search, userList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FriendsRemoveUiState()
        )

    fun updateFriendsRemoveSearch(text: String) {
        _friendsRemoveSearch.value = text
    }

    fun selectFriend(userId: String, isSelect: Boolean) {
        friendsRemoveUserList.value.find { it.friend.userId == userId }?.let {
            it.isSelect = isSelect
        }
        _filteredFriendsRemoveUserList.value.find { it.friend.userId == userId }?.let {
            it.isSelect = isSelect
        }

    }

    fun selectAllFriends(isAllChecked: Boolean) {
        friendsRemoveUserList.value.map { it.isSelect = isAllChecked }
        _filteredFriendsRemoveUserList.value.map { it.isSelect = isAllChecked }

    }

    fun search(name: String) {
        val filtering = friendsRemoveUserList.value.filter { it.friend.name.contains(name) }
        if (name.isEmpty()) {
            _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
        } else {
            _filteredFriendsRemoveUserList.value = filtering
        }
    }

    fun fetchReadAllFriends() {
        viewModelScope.launch {
            when (val result = readAllFriendsUseCase().first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> Log.d(
                    "daeyoung",
                    "message: ${result.message}\nexception: ${result.exception}"
                )

                is ApiState.Success -> {
                    _friendsRemoveUserList.value =
                        result.data.map { FriendUiState(it.asDomain(), false) }
                    _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
                }
            }
        }
    }

}