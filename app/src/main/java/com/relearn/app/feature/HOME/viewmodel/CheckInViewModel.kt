package com.relearn.app.feature.HOME.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relearn.app.feature.HOME.domain.repository.CheckInRepository
import com.relearn.app.feature.HOME.model.DailyCheckIn

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor(
    private val repository: CheckInRepository
) : ViewModel() {

    private val _checkIn = MutableStateFlow<DailyCheckIn?>(null)
    val checkIn: StateFlow<DailyCheckIn?> = _checkIn

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    fun submitCheckIn(userId: String, mood: String, energyLevel: Int) {
        val checkInEntry = DailyCheckIn(
            userId = userId,
            mood = mood,
            energyLevel = energyLevel
        )

        viewModelScope.launch {
            Log.d("CheckInVM", "submitCheckIn: $checkInEntry")
            _isSubmitting.value = true
            try {
                repository.saveCheckIn(checkInEntry)
                _checkIn.value = checkInEntry
                Log.d("CheckInVM", "Check-in saved successfully")
            } catch (e: Exception) {
                Log.e("CheckInVM", "Error saving check-in", e)
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    fun loadCheckInForToday(userId: String, date: Long) {
        viewModelScope.launch {
            val result = repository.getCheckInForToday(userId, date)
            _checkIn.value = result
        }
    }
}