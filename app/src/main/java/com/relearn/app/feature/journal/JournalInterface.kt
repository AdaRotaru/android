package com.relearn.app.feature.journal

interface JournalInterface {
    suspend fun submitJournalEntry(content: String): JournalEntry
}
