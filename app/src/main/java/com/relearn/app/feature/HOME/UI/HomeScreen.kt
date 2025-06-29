package com.relearn.app.feature.HOME.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.viewmodel.CheckInViewModel
import com.relearn.app.feature.HOME.viewmodel.ChallengeViewModel
import com.relearn.app.feature.HOME.viewmodel.ToDoViewModel
import com.relearn.app.feature.HOME.ui.components.CheckInCard
import com.relearn.app.feature.HOME.ui.components.ChallengeCard
import com.relearn.app.feature.HOME.ui.components.ToDoSection

@Composable
fun HomeScreen(
    checkInViewModel: CheckInViewModel,
    challengeViewModel: ChallengeViewModel,
    toDoViewModel: ToDoViewModel,
    userId: String
) {
    val backgroundColor = Color(0xFFFFF7FA)
    val scrollState = rememberScrollState()

    val checkIn by checkInViewModel.checkIn.collectAsState()
    val challenges by challengeViewModel.challenges.collectAsState()
    val tasks by toDoViewModel.tasks.collectAsState()

    // mock user habits - to be replaced with real data from Firestore
    val userHabits = remember { listOf("Fumat", "Procrastinare", "Scroll excesiv") }
    val isSubmitting by checkInViewModel.isSubmitting.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CheckInCard(
            onSubmit = { mood, energy ->
                checkInViewModel.submitCheckIn(userId, mood, energy)
            }
        )
        if (isSubmitting) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (checkIn != null) {
            challengeViewModel.setContext(checkIn!!, userHabits)
            challengeViewModel.loadChallenges()
        }

        Text(
            text = "Provocări pentru azi",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp)
        )

        challenges.take(2).forEach { challenge ->
            ChallengeCard(
                challenge = challenge,
                onToggleCompleted = {
                    challengeViewModel.completeChallenge(challenge.id)
                },
                onSkip = {
                    challengeViewModel.skipChallenge(challenge.id)
                }
            )
        }

        Text(
            text = "Task-uri personale",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp)
        )

        ToDoSection(
            tasks = tasks,
            onAdd = { title -> toDoViewModel.addTask(userId, title) },
            onToggleDone = { task -> toDoViewModel.toggleTaskDone(userId, task) },
            onDelete = { taskId -> toDoViewModel.deleteTask(userId, taskId) }
        )

    }
}
