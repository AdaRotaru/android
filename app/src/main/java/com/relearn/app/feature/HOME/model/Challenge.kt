package com.relearn.app.feature.HOME.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Entity(tableName = "challenges")
@Parcelize
data class Challenge(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val description: String = "",
    val habitTarget: String = "",
    val mood: String = "",
    val status: ChallengeStatus = ChallengeStatus.PENDING,
    val categorie: String = "",
    val nivel: DifficultyLevel = DifficultyLevel.STARTER,
    val activ: Boolean = true,
    val timestamp: Long = System.currentTimeMillis(),
    val date: Long = 0L
) : Parcelable



