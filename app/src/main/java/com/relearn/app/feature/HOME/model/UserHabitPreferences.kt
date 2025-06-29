package com.relearn.app.feature.HOME.model

data class UserHabitPreferences(
    val userId: String = "",
    val selectedHabits: List<String> = emptyList()
)