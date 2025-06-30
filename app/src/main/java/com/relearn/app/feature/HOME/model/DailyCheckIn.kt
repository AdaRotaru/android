package com.relearn.app.feature.HOME.model

import java.time.LocalDate

data class DailyCheckIn(
    val userId: String = "",
    val date: Long = LocalDate.now().toEpochDay(),
    val mood: String = "",
    val energyLevel: Int = 0
)
