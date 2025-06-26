package com.relearn.app.feature.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object RegisterSuccess : AuthState()
    object LoginSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        gender: String,
        //birthDate: String,
        preferences: List<String>
    ) {
        _authState.value = AuthState.Loading

        authRepository.register(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            gender = gender,
            //birthDate = birthDate,
            preferences = preferences
        ) { success, error ->
            _authState.value = if (success) {
                AuthState.RegisterSuccess
            } else {
                AuthState.Error(error ?: "Eroare necunoscută")
            }
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
