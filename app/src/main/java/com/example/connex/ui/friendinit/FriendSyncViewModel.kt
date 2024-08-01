package com.example.connex.ui.friendinit

import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.connex.utils.syncCallLog
import com.example.connex.utils.syncContact
import com.example.domain.model.login.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class FriendSyncViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

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

        _contacts.value =
            resortedBy(syncContact(resolver).sortedBy { it.name }.toMutableList(), callLogs)
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


}