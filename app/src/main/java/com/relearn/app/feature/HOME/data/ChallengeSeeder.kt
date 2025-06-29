package com.relearn.app.feature.HOME.data

import com.google.firebase.firestore.FirebaseFirestore
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus
import com.relearn.app.feature.HOME.model.DifficultyLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

object ChallengeSeeder {

    private val habits = listOf(
        "Anxietate", "Oboseală", "Auto-critică",
        "Procrastinare", "Lipsă de focus", "Demotivare",
        "Somn neregulat", "Sedentarism", "Mâncat impulsiv",
        "Haos în rutină", "Lipsă de planificare", "Gestionare slabă a timpului"
    )

    private val moods = listOf(
        "Fericit", "Trist", "Agitat", "Calm", "Neliniștit", "Motivat"
    )

    private val difficulties = listOf(
        DifficultyLevel.STARTER, DifficultyLevel.FOCUS, DifficultyLevel.FLOW
    )

    private val firestore = FirebaseFirestore.getInstance()
    private val challengesCollection = firestore.collection("challenges")

    fun seedChallenges() {
        CoroutineScope(Dispatchers.IO).launch {
            for (habit in habits) {
                for (mood in moods) {
                    for (difficulty in difficulties) {
                        val challenge = Challenge(
                            id = UUID.randomUUID().toString(),
                            title = "Provocare pentru $habit",
                            description = "Fă un exercițiu pentru a gestiona $habit când ești $mood și ai energie ${difficulty.name}",
                            habitTarget = habit,
                            mood = mood,
                            nivel = difficulty,
                            status = ChallengeStatus.PENDING,
                            timestamp = System.currentTimeMillis(),
                            activ = true,
                            categorie = "Auto"
                        )
                        challengesCollection.document(challenge.id).set(challenge)
                    }
                }
            }
        }
    }
}
