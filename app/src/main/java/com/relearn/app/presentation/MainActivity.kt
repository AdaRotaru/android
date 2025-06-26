package com.relearn.app.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import com.relearn.app.core.ui.theme.ReLearnTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.relearn.app.navigation.AppNav
import com.relearn.app.navigation.BottomBar
import com.relearn.app.navigation.Screen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReLearnTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // BottomBar apare DOAR pe ecranele principale
                val showBottomBar = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Journal.route,
                    Screen.Progress.route,
                    Screen.Settings.route,
                    Screen.OfflineHabits.route
                )

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(navController = navController, currentRoute = currentRoute)
                        }
                    }
                ) { innerPadding ->
                    AppNav(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

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