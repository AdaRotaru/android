package com.relearn.app.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.core.ui.theme.ReLearnTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.relearn.app.ui.challenge.AddChallengeScreen
import com.relearn.app.feature.challenges.viewmodel.ChallengeViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.relearn.app.feature.challenges.ui.ChallengeListScreen
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.relearn.app.navigation.AppNavGraph


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReLearnTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }

        }
//            val viewModel: ChallengeViewModel = hiltViewModel()
//
//            ChallengeListScreen(viewModel = viewModel)
//
//            ReLearnTheme {
//
//                val navController = rememberNavController()
//                AppNavGraph(navController = navController)
//
//                var showAddScreen by remember { mutableStateOf(true) }
//
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column(modifier = Modifier.padding(innerPadding)) {
//                        if (showAddScreen) {
//                            AddChallengeScreen(viewModel = viewModel, onViewChallengesClick = { showAddScreen = false })
//                            Button(
//                                onClick = { showAddScreen = false },
//                                modifier = Modifier.padding(16.dp)
//                            ) {
//                                Text("Vezi Challenge-uri")
//                            }
//                        } else {
//                            ChallengeListScreen(viewModel = viewModel)
//                            Button(
//                                onClick = { showAddScreen = true },
//                                modifier = Modifier.padding(16.dp)
//                            ) {
//                                Text("Adaugă Challenge")
//                            }
//                        }
//                    }
//                }
//            }
//        }
        fun testFirebaseConnection() {
            Firebase.auth.signInWithEmailAndPassword("test@relearn.com", "test1234")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FirebaseTest", "Login success: ${task.result.user?.uid}")
                    } else {
                        Log.e("FirebaseTest", "Login failed", task.exception)
                    }
                }
        }

        fun testFirestoreWrite() {
            val db = Firebase.firestore
            val challenge = hashMapOf(
                "title" to "Test Challenge",
                "description" to "This is a test challenge",
                "completed" to false
            )

            db.collection("challenges")
                .add(challenge)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "FirestoreTest",
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w("FirestoreTest", "Error adding document", e)
                }
        }
    }
}
