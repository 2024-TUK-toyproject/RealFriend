package com.example.domain.usecase

import com.example.domain.entity.contact.ExtractedCallLog
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class SyncCallLogsUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    operator fun invoke(callLogs: List<ExtractedCallLog>) =
        contactRepository.syncCallLogs(callLogs)
}