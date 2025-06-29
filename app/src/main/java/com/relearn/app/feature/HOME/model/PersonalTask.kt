package com.relearn.app.feature.HOME.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "personal_tasks")
@Parcelize
data class PersonalTask(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val title: String = "",
    val isDone: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
