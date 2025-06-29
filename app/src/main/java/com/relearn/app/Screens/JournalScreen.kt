package com.relearn.app.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.relearn.app.feature.challenges.ui.ChallengeListScreen
import com.relearn.app.feature.challenges.viewmodel.ChallengeViewModel
import com.relearn.app.ui.challenge.AddChallengeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(viewModel: ChallengeViewModel = hiltViewModel()) {
    var showList by remember { mutableStateOf(false) }

    if (showList) {
        ChallengeListScreen(viewModel = viewModel)
    } else {
        AddChallengeScreen(
            viewModel = viewModel,
            onViewChallengesClick = { showList = true }
        )
    }
}
