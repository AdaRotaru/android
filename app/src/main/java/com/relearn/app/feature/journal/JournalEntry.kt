package com.relearn.app.feature.journal

import com.relearn.app.feature.HOME.model.MoodType

data class JournalEntry(
    val id: String = "",
    val userId: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val content: String = "",
    val mood: MoodType? = null,
    val suggestions: List<String> = emptyList()
)