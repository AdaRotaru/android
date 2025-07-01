package com.relearn.app.feature.HOME.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.relearn.app.feature.HOME.ui.components.ChallengeCard
import com.relearn.app.feature.HOME.ui.components.CheckInCard
import com.relearn.app.feature.HOME.ui.components.ToDoSection
import com.relearn.app.feature.HOME.viewmodel.ChallengeViewModel
import com.relearn.app.feature.HOME.viewmodel.CheckInViewModel
import com.relearn.app.feature.HOME.viewmodel.ToDoViewModel
import com.relearn.app.feature.HOME.viewmodel.UserPreferencesViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    checkInViewModel: CheckInViewModel,
    challengeViewModel: ChallengeViewModel,
    toDoViewModel: ToDoViewModel,
    userPreferencesViewModel: UserPreferencesViewModel,
    userId: String
) {
    Log.d("HomeScreen", "userId = $userId")
    LaunchedEffect(Unit) {
        toDoViewModel.loadTasks(userId)
        checkInViewModel.loadCheckInForToday(userId, LocalDate.now().toEpochDay())
        userPreferencesViewModel.loadUserPreferences(userId)
    }

    val backgroundColor = Color(0xFFFFF7FA)
    val scrollState = rememberScrollState()

    val userHabits by userPreferencesViewModel.habits.collectAsState()
    val checkIn by checkInViewModel.checkIn.collectAsState()
    val challenges by challengeViewModel.challenges.collectAsState()
    val challengeError by challengeViewModel.error.collectAsState()
    val tasks by toDoViewModel.tasks.collectAsState()
    val isSubmitting by checkInViewModel.isSubmitting.collectAsState()
    val isRefreshing by challengeViewModel.isLoading.collectAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(tasks) {
        Log.d("HomeScreen", "Tasks updated: size=${tasks.size}, tasks=$tasks")
    }

    LaunchedEffect(Unit) {
        snapshotFlow { checkIn to userHabits }
            .collect { (checkInValue, habits) ->
                Log.d("HomeScreen", "snapshotFlow triggered: checkIn=$checkInValue, habits=$habits, challenges=${challenges.size}")
                if (checkInValue != null && habits.isNotEmpty() && challenges.isEmpty()) {
                    Log.d("HomeScreen", "Triggering challengeViewModel.loadChallenges()")
                    challengeViewModel.setContext(checkInValue, habits)
                    challengeViewModel.loadChallenges()
                }
            }
    }

    SwipeRefresh(
        state = refreshState,
        onRefresh = { challengeViewModel.refreshChallenges() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (checkIn == null) {
                CheckInCard(
                    onSubmit = { mood, energy ->
                        checkInViewModel.submitCheckIn(userId, mood, energy)
                    }
                )
                if (isSubmitting) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = Color(0xFFCFD9FF),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "✔ Check-in completat",
                        color = Color(0xFF4A4A68),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Text(
                text = "🌸 Provocări pentru azi",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color(0xFF7B1FA2),
                modifier = Modifier.padding(start = 8.dp)
            )

            if (challengeError != null) {
                Text(
                    text = "Eroare: $challengeError",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
            }

            challenges.forEach { challenge ->
                ChallengeCard(
                    challenge = challenge,
                    onToggleCompleted = { challengeViewModel.completeChallenge(challenge.id) },
                    onSkip = { challengeViewModel.skipChallenge(challenge.id) },
                    onRemove = {
                        challengeViewModel.removeChallengeFromUI(challenge.id)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            ToDoSection(
                userId = userId,
                tasks = tasks,
                onAdd = { title -> toDoViewModel.addTask(userId, title) },
                onToggleDone = { task -> toDoViewModel.toggleTaskDone(userId, task) },
                onDelete = { taskId -> toDoViewModel.deleteTask(userId, taskId) }
            )
}}}
