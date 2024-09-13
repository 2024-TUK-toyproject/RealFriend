package com.example.connex.ui.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.syncContact
import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import com.example.domain.model.response.ContactInfo
import com.example.domain.model.response.Friend
import com.example.domain.model.response.asDomain
import com.example.domain.usecase.ReadAllFriendsUseCase
import com.example.domain.usecase.friend.AddFriendUseCase
import com.example.domain.usecase.friend.DeleteFriendUseCase
import com.example.domain.usecase.friend.ReadAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
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
    val readAllContactsUseCase: ReadAllContactsUseCase,
    val deleteFriendUseCase: DeleteFriendUseCase,
    val addFriendUseCase: AddFriendUseCase,
    @ApplicationContext val applicationContext: Context,
) : ViewModel() {


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

    private val _friendsAddUserList = MutableStateFlow(emptyList<ContactInfo>())
    val friendsAddUserList: StateFlow<List<ContactInfo>> = _friendsAddUserList.asStateFlow()

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

    fun selectFriend(userId: Long, isSelect: Boolean) {
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

    private fun filterNotFriendsInMyAddressBook(): List<Contact> {
        // 연락처 중 친구인 연라거
        val filteringFriends =
            friendsUserList.value.filter { it.isFriend }

        val myAddressBook = syncContact(applicationContext.contentResolver)
        return myAddressBook.filter { contact ->
            val isFriend = filteringFriends.firstOrNull { filtered ->
                filtered.name == contact.name && filtered.phone == contact.phone
            }
            isFriend == null
        }
    }

    // 기기의 연락처 중에서 친구가 아닌 연락처만 추출
    fun fetchReadAddedFriends(notResponse: () -> Unit) {
        val filteringContact = filterNotFriendsInMyAddressBook()
        Log.d("daeyoung", "fetchReadAddedFriends: ${filteringContact}")

        viewModelScope.launch {
            when (val result = readAllContactsUseCase(filteringContact).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "message: ${result.message}\nexception: ${result.exception}")
                    if (result.exception is ConnectException) { notResponse() }
                }

                is ApiState.Success -> {
                    _friendsAddUserList.update {
                        result.data.sortedWith(compareBy({ it.isExist }, { it.name }))
                    }
                }

            }
        }
    }


    fun fetchReadAllFriends(notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = readAllFriendsUseCase().first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        Log.d(
                            "daeyoung",
                            "message: ${result.message}\nexception: ${result.exception}"
                        )
                        notResponse()
                    }
                }

                is ApiState.Success -> {
                    result.data.also { list ->
                        _friendsRemoveUserList.value =
                            list.map { FriendUiState(it.asDomain(), false) }
                                .filter { it.friend.isFriend }
//                        _friendsUserList.value = list.map { it.asDomain().copy(isFriend = true) }
                        _friendsUserList.value = list.map { it.asDomain() }
                    }
                    _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
                    _filteredFriendsUserList.value = friendsUserList.value
                }
            }
        }
    }

    fun fetchDeleteFriend(notResponse: () -> Unit) {
        viewModelScope.launch {
            val friendId =
                friendsRemoveUserList.value.filter { it.isSelect }
                    .map { it.friend.userId.toString() }
            when (val result = deleteFriendUseCase(friendId).first()) {
                is ApiState.Error -> Log.d("daeyoung", "message: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    Log.d(
                        "daeyoung",
                        "message: ${result.message}\nexception: ${result.exception}"
                    )
                    if (result.exception is ConnectException) {
                        Log.d("daeyoung", "ConnectException: ${result.message}\n${result.exception}")
                        notResponse()
                    }
                }

                is ApiState.Success -> {
                    val refreshFriendsRemoveUserList =
                        friendsRemoveUserList.value.toMutableList().also { list ->
                            list.removeAll { it.isSelect }
                        }
                    _friendsRemoveUserList.value = refreshFriendsRemoveUserList
                    _filteredFriendsRemoveUserList.value = friendsRemoveUserList.value
                }
            }
        }
    }

    fun fetchAddFriend(friendId: Long, onSuccess: () -> Unit, notResponse: () -> Unit) {
        viewModelScope.launch {
            Log.d("daeyoung", "friendId: $friendId")
            when (val result = addFriendUseCase(friendId).first()) {
                is ApiState.Error -> TODO()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        Log.d("daeyoung", "ConnectException: ${result.message}\n${result.exception}")
                        notResponse()
                    }
                }
                is ApiState.Success -> {
                    onSuccess()
                }
            }
        }
    }

}