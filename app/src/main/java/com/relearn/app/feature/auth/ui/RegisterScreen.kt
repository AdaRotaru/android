package com.relearn.app.feature.auth.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.relearn.app.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNext: (firstName: String, lastName: String, gender: String, email: String, password: String) -> Unit,
    onLoginClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val genderOptions = listOf("F", "M", "Altul")
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(genderOptions[0]) }
    var expandedGender by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val accentColor = Color(0xFFBA68C8)
    val backgroundColor = Color(0xFFFFF7FA)
    val textColor = Color(0xFF444444)
    val fieldTextColor = Color(0xFF888888)
    val buttonColor = Color(0xFFAB47BC)
    val innerScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .imePadding()
            .pointerInput(Unit) { detectTapGestures { keyboardController?.hide() } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
              //  .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
            .imePadding(),
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
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 600.dp)
                        .verticalScroll(innerScroll)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Creează cont",
                        style = MaterialTheme.typography.titleLarge.copy(color = textColor),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val formFields = listOf(
                        Triple("Prenume", firstName) { it: String -> firstName = it },
                        Triple("Nume", lastName) { it: String -> lastName = it },
                        Triple("Email", email) { it: String -> email = it }
                    )

                    formFields.forEach { (label, value, onChange) ->
                        TextField(
                            value = value,
                            onValueChange = onChange,
                            label = { Text(label, color = fieldTextColor) },
                            singleLine = true,
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
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandedGender,
                        onExpandedChange = { expandedGender = !expandedGender }
                    ) {
                        TextField(
                            value = selectedGender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gen", color = fieldTextColor) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedGender) },
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
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedGender,
                            onDismissRequest = { expandedGender = false }
                        ) {
                            genderOptions.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        selectedGender = it
                                        expandedGender = false
                                    }
                                )
                            }
                        }
                    }

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Parolă", color = fieldTextColor) },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        singleLine = true,
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

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                                errorMessage = "Completează toate câmpurile!"
                            } else {
                                errorMessage = null
                                onNext(firstName, lastName, selectedGender, email, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                    ) {
                        Text("Continuă", color = Color.White)
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Ai deja cont? Login",
                        color = accentColor,
                        modifier = Modifier
                            .clickable(onClick = onLoginClick)
                            .padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
