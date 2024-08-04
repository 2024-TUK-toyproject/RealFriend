package com.example.connex.ui.friendinit

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.syncCallLog
import com.example.connex.utils.syncContact
import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import com.example.domain.usecase.SyncContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhoneUiState(
    val contact: Contact,
    val isSelect: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FriendSyncViewModel @Inject constructor(
    val syncContactsUseCase: SyncContactsUseCase,
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    var userId: Long = 0L

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _filteredContacts = MutableStateFlow<List<Contact>>(emptyList())
    val filteredContacts: StateFlow<List<Contact>> = _filteredContacts.asStateFlow()

    init {
        syncContracts(applicationContext.contentResolver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun syncContracts(resolver: ContentResolver) {
        val callLogs = syncCallLog(resolver)
            .map { it.name to it.phone }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .map { it.first }
        Log.d("daeyoung", "callLogs: $callLogs")


        _contacts.value = resortedBy(syncContact(resolver).sortedBy { it.name }.toMutableList(), callLogs)
        _filteredContacts.value = contacts.value

    }

    fun resortedBy(list1: MutableList<Contact>, list2: List<Pair<String?, String?>>): List<Contact> {
        list1.removeAll { contact -> contact.name in list2.map { it.first } }
        list2.reversed().forEach {
            list1.add(0, Contact(it.first!!, it.second!!))
        }
        return list1
    }

    fun search(name: String) {
        val filtering = contacts.value.filter { it.name.contains(name) }
        if (name.isEmpty()) {
            _filteredContacts.value = contacts.value
        } else {
            _filteredContacts.value = filtering
        }
    }

//    fun fetchSyncContacts() {
//        viewModelScope.launch {
//            when (val result = syncContactsUseCase(
//                userId = userId,
//                contacts = ,
//            ).first()) {
//                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
//                ApiState.Loading -> TODO()
//                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
//                is ApiState.NotResponse -> TODO()
//            }
//        }
//    }


}