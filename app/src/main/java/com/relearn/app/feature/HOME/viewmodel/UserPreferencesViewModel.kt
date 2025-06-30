package com.relearn.app.feature.HOME.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _habits = MutableStateFlow<List<String>>(emptyList())
    val habits: StateFlow<List<String>> = _habits

    fun loadUserPreferences(userId: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("users")
                    .document(userId)
                    .get()
                    .await()
                val raw = snapshot.get("preferences") as? List<*>
                val prefs = raw?.filterIsInstance<String>() ?: emptyList()
                _habits.value = prefs
                Log.d("UserPrefsVM", "Loaded preferences for $userId: $prefs")
            } catch (e: Exception) {
                Log.e("UserPrefsVM", "Error loading preferences", e)
                _habits.value = emptyList()
            }
        }
    }
}