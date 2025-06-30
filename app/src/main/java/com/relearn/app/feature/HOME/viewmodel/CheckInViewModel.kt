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
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CheckInViewModel @Inject constructor( //functia care primeste 3 parametri
    private val repository: CheckInRepository
) : ViewModel() {

    private val _checkIn = MutableStateFlow<DailyCheckIn?>(null)
    val checkIn: StateFlow<DailyCheckIn?> = _checkIn

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    fun submitCheckIn(userId: String, mood: String, energyLevel: Int) {
        val checkInEntry = DailyCheckIn( //creem un obiect dailyCheckIn cu valorile primite
            userId = userId,
            mood = mood,
            energyLevel = energyLevel,
            date = LocalDate.now().toEpochDay()
        )

        viewModelScope.launch {//un coroutine necesar pentru apeluri asincrone plicația rămâne fluidă, iar datele se procesează în fundal
            Log.d("CheckInVM", "submitCheckIn: $checkInEntry")
            _isSubmitting.value = true
            try {
                repository.saveCheckIn(checkInEntry) //salveaza obiectul in db
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