package com.example.domain.usecase

import com.example.domain.model.login.CallLog
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class SyncCallLogsUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    operator fun invoke(callLogs: List<CallLog>) =
        contactRepository.syncCallLogs(callLogs)
}