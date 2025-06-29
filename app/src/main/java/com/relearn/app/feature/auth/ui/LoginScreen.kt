package com.relearn.app.feature.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.relearn.app.R

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

    val savedEmail = sharedPref.getString("email", null)
    val savedPassword = sharedPref.getString("password", null)
    val previouslyRemembered = sharedPref.getBoolean("rememberMe", false)

    LaunchedEffect(Unit) {
        if (previouslyRemembered && savedEmail != null && savedPassword != null) {
            email = savedEmail
            password = savedPassword
            rememberMe = true
        }
    }

    val focusManager = LocalFocusManager.current
    val innerScroll = rememberScrollState()

    val accentColor = Color(0xFF7E57C2)
    val backgroundColor = Color(0xFFFFF7FA)
    val textColor = Color(0xFF444444)
    val fieldTextColor = Color(0xFF888888)
    val buttonColor = Color(0xFF7E57C2)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .imePadding()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.relearn_logo),
                contentDescription = "ReLearn Logo",
                modifier = Modifier
                    .height(80.dp)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(innerScroll)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Bun venit înapoi",
                        style = MaterialTheme.typography.titleLarge.copy(color = textColor),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Text(
                        text = "Autentifică-te pentru a continua",
                        style = MaterialTheme.typography.bodyMedium.copy(color = fieldTextColor),
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 12.dp)
                    )

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = fieldTextColor) },
                        singleLine = true,
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
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = accentColor,
                            unfocusedIndicatorColor = accentColor,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedLabelColor = fieldTextColor,
                            unfocusedLabelColor = fieldTextColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Parolă", color = fieldTextColor) },
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
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = accentColor,
                            unfocusedIndicatorColor = accentColor,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedLabelColor = fieldTextColor,
                            unfocusedLabelColor = fieldTextColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                        Text("Ține-mă minte", color = fieldTextColor)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                errorMessage = "Te rugăm să completezi toate câmpurile"
                            } else {
                                errorMessage = null
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
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                        Text("Autentificare", color = Color.White)
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Nu ai cont? Înregistrează-te",
                        color = accentColor,
                        modifier = Modifier
                            .clickable(onClick = onNavigateToRegister)
                            .padding(8.dp)
                    )

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

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

