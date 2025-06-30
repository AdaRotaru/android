package com.relearn.app.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object RegisterSuccess : AuthState()
    object LoginSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        gender: String,
        preferences: List<String>,
        onResult: (Boolean) -> Unit
    ) {
        _authState.value = AuthState.Loading

        authRepository.register(email, password, firstName, lastName, gender, preferences) { success, error ->
            _authState.value = if (success) AuthState.RegisterSuccess else AuthState.Error(error ?: "Eroare")
            onResult(success)
        }
    }




    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading

        authRepository.login(email, password) { success, error ->
            _authState.value = if (success) {
                AuthState.LoginSuccess
            } else {
                AuthState.Error(error ?: "Eroare necunoscută")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthState.Idle
    }

    fun isUserLoggedIn(): Boolean = authRepository.isUserLoggedIn()


}
