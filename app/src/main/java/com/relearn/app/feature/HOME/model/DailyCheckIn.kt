package com.relearn.app.feature.HOME.model

data class DailyCheckIn(
    val userId: String = "",
    val date: Long = System.currentTimeMillis(),
    val mood: String = "",
    val energyLevel: Int = 0
)
