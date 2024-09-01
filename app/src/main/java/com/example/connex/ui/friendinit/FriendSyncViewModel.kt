package com.example.connex.ui.friendinit

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.syncCallLog
import com.example.connex.utils.syncContact
import com.example.data.datastore.TokenManager
import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import com.example.domain.usecase.SyncContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

class PhoneUiState(
    val contact: Contact,
    initialChecked: Boolean = false
) {
    var isSelect by mutableStateOf(initialChecked)
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FriendSyncViewModel @Inject constructor(
    val syncContactsUseCase: SyncContactsUseCase,
    val tokenManager: TokenManager,
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    var userId: Long = 0L

    private val _contacts = MutableStateFlow<List<PhoneUiState>>(emptyList())
    val contacts: StateFlow<List<PhoneUiState>> = _contacts.asStateFlow()

    private val _filteredContacts = MutableStateFlow<List<PhoneUiState>>(emptyList())
    val filteredContacts: StateFlow<List<PhoneUiState>> = _filteredContacts.asStateFlow()

    val count = derivedStateOf {
        filteredContacts.value.count { it.isSelect }
    }

    init {
        syncContracts(applicationContext.contentResolver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun syncContracts(resolver: ContentResolver) {
        viewModelScope.launch {
            val callLogs = syncCallLog(tokenManager, resolver, 100)
                .map { it.name to it.phone }
                .groupingBy { it }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .map { PhoneUiState(Contact(it.first.first!!, it.first.second!!), false) }

            _contacts.value = resortedBy(
                syncContact(resolver).sortedBy { it.name }
                    .map { PhoneUiState(Contact(it.name, it.phone), false) }
                    .toMutableList(),
                callLogs
            )
            _filteredContacts.value = contacts.value
        }
    }

    fun resortedBy(list1: MutableList<PhoneUiState>, list2: List<PhoneUiState>): List<PhoneUiState>
    {
        list1.removeAll { contact -> contact.contact.name in list2.map { it.contact.name } }
        list2.reversed().forEach {
            list1.add(0, PhoneUiState(Contact(it.contact.name, it.contact.phone), false))
        }
        return list1
    }

    fun search(name: String) {
        val filtering = contacts.value.filter { it.contact.name.contains(name) }
        if (name.isEmpty()) {
            _filteredContacts.value = contacts.value
        } else {
            _filteredContacts.value = filtering
        }
    }

    fun selectCard(phone: String, isSelect: Boolean) {
//        val index = contacts.value.indexOfFirst { it.contact.phone == phone }
//        if (index != -1) {
//            val item = contacts.value[index]
//            _contacts.value.toMutableList()[index] = item.copy(isSelect = isSelect)
//        }
        _contacts.value.find { it.contact.phone == phone }?.let {
            it.isSelect = isSelect
        }
    }
    fun fetchSyncContacts(onSuccess: () -> Unit, notResponse: () -> Unit) {
        viewModelScope.launch {
            val selectedContacts = contacts.value.filter { it.isSelect }.map { it.contact }

            when (val result = syncContactsUseCase(
                userId = userId,
                contacts = selectedContacts,
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        notResponse()
                    }
                }
            }
        }
    }


}