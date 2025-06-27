package com.relearn.app.Screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.http.ApiViewModel


@Composable
fun ExtraScreen(viewModel: ApiViewModel = viewModel()) {
    var selected by remember { mutableStateOf("posts") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { selected = "posts" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == "posts") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Postări")
            }

            Button(
                onClick = { selected = "users" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == "users") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Useri")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selected) {
            "posts" -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.posts.value) { post ->
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = post.title, fontWeight = FontWeight.Bold)
                        Text(text = post.body)
                    }
                    Divider()
                }
            }

            "users" -> LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.users.value) { user ->
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = user.name, fontWeight = FontWeight.Bold)
                        Text(text = user.email)
                    }
                    Divider()
                }
            }
        }
    }
}
