package com.relearn.app.feature.auth

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    val sharedPref = remember {
        context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    val state by authViewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Dropdown
    val savedEmail = sharedPref.getString("email", null)
    val savedPassword = sharedPref.getString("password", null)
    val previouslyRemembered = sharedPref.getBoolean("rememberMe", false)

    // Auto-fill pt remember
    LaunchedEffect(Unit) {
        if (previouslyRemembered && savedEmail != null && savedPassword != null) {
            email = savedEmail
            password = savedPassword
            rememberMe = true
        }
    }

    // Hide keyboard
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
            .padding(32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Login", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            // Email TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                trailingIcon = {
                    if (email.isNotEmpty()) {
                        IconButton(onClick = {
                            email = ""
                            password = ""
                            rememberMe = false
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Șterge emailul")
                        }
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Parolă") },
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Row {
                        if (password.isNotEmpty()) {
                            IconButton(onClick = { password = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Șterge parola")
                            }
                        }
                        IconButton(onClick = { showPassword = !showPassword }) {
                            val icon = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            Icon(icon, contentDescription = "Toggle password visibility")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                Text("Ține-mă minte")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Te rugăm să completezi toate câmpurile"
                } else {
                    errorMessage = null

                    // Save sau clear preferințele
                    with(sharedPref.edit()) {
                        if (rememberMe) {
                            putString("email", email)
                            putString("password", password)
                            putBoolean("rememberMe", true)
                        } else {
                            clear()
                        }
                        apply()
                    }

                    authViewModel.login(email, password)
                }
            }) {
                Text("Autentificare")
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("Nu ai cont? Înregistrează-te")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (state) {
                is AuthState.Loading -> CircularProgressIndicator()
                is AuthState.LoginSuccess -> {
                    LaunchedEffect(Unit) {
                        onLoginSuccess()
                    }
                }
                is AuthState.Error -> {
                    Text(
                        text = (state as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }
}

