package com.relearn.app.feature.challenges

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
    val status: ChallengeStatus = ChallengeStatus.PENDING,
    val categorie: String = "",
    val nivel: DifficultyLevel = DifficultyLevel.STARTER,
    val activ: Boolean = true
) : Parcelable

enum class ChallengeStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
enum class DifficultyLevel {
    STARTER, FOCUS, FLOW
}
