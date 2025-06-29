package com.relearn.app.feature.http

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PostListScreen(viewModel: ApiViewModel = viewModel()) {
    LazyColumn {
        items(viewModel.posts.value) { post ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = post.title, fontWeight = FontWeight.Bold)
                Text(text = post.body)
            }
            Divider()
        }
    }
}
