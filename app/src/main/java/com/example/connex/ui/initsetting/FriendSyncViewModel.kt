package com.example.connex.ui.initsetting

import android.content.ContentResolver
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.connex.utils.syncContact
import com.example.domain.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FriendSyncViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context
) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private fun syncContracts(resolver: ContentResolver) {
        _contacts.value = syncContact(resolver).sortedBy { it.name }
    }

    init {
        syncContracts(applicationContext.contentResolver)
    }
}