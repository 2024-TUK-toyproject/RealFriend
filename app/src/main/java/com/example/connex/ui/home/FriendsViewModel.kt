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

data class FriendsUiState(
    val search: String = "",
    val userList: List<Friend> = emptyList(),
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

    private val _friendsSearch = MutableStateFlow("")
    val friendsSearch: StateFlow<String> = _friendsSearch.asStateFlow()

    private val _friendsUserList = MutableStateFlow(emptyList<Friend>())
    val friendsUserList: StateFlow<List<Friend>> = _friendsUserList.asStateFlow()

    private val _filteredFriendsUserList = MutableStateFlow(emptyList<Friend>())
    val filteredFriendsUserList: StateFlow<List<Friend>> =
        _filteredFriendsUserList.asStateFlow()

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

    val friendsUiState =
        combine(_friendsSearch, _filteredFriendsUserList) { search, userList ->
            FriendsUiState(search, userList)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FriendsUiState()
        )

    fun updateFriendsRemoveSearch(text: String) {
        _friendsRemoveSearch.value = text
    }

    fun updateFriendsSearch(text: String) {
        _friendsSearch.value = text
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

    fun friendsRemoveUserSearch(name: String) {
        val filtering = friendsRemoveUserList.value.filter { it.friend.name.contains(name) }
        if (name.isEmpty()) {
            _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
        } else {
            _filteredFriendsRemoveUserList.value = filtering
        }
    }
    fun friendsUserSearch(name: String) {
        val filtering = friendsUserList.value.filter { it.name.contains(name) }
        if (name.isEmpty()) {
            _filteredFriendsUserList.value = friendsUserList.value
        } else {
            _filteredFriendsUserList.value = filtering
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
                    result.data.also { list ->
                        _friendsRemoveUserList.value = list.map { FriendUiState(it.asDomain(), false) }
                        _friendsUserList.value = list.map { it.asDomain().copy(isFriend = true) }
                    }
                    _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
                    _filteredFriendsUserList.value = friendsUserList.value
                }
            }
        }
    }

}