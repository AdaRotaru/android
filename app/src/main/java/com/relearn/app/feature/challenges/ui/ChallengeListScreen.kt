package com.relearn.app.feature.challenges.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.challenges.ChallengeStatus
import com.relearn.app.feature.challenges.ui.components.ChallengeCard
import com.relearn.app.feature.challenges.ChallengeViewModel

@Composable
fun ChallengeListScreen(viewModel: ChallengeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val challenges = uiState.challenges
    val selectedStatus = uiState.selectedStatus


    val tabs = listOf("Pending", "Completed")
    val selectedIndex = if (selectedStatus == ChallengeStatus.PENDING) 0 else 1


    Column(modifier = Modifier.fillMaxSize()) {

        TabRow(selectedTabIndex = selectedIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        val status = if (index == 0) ChallengeStatus.PENDING else ChallengeStatus.COMPLETED
                        viewModel.setStatusFilter(status)
                    },
                    text = { Text(title) }
                )
            }
        }
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(challenges) { challenge ->
                    ChallengeCard(
                        challenge = challenge,
                        onToggleCompleted = { completed ->
                            val newStatus = if (completed) ChallengeStatus.COMPLETED else ChallengeStatus.PENDING
                            viewModel.updateChallengeStatus(challenge.id, newStatus)
                        },
                        onDelete = {
                            viewModel.deleteChallenge(challenge.id)
                        }
                    )
                }
            }
        }
    }

