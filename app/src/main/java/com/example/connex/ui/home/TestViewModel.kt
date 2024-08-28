package com.example.connex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    val tokenManager: TokenManager
): ViewModel() {

    fun logout(navigate: () -> Unit) {
        viewModelScope.launch {
            with(tokenManager) {
                deleteAccessToken()
                deleteRefreshToken()
                deleteFCMToken()
            }
            navigate()
        }
    }
}