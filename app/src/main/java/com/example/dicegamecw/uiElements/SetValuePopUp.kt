package com.example.dicegamecw

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SetValue(
    onDismiss: () -> Unit,
    onSubmit: (Int) -> Unit,
    onUseDefault: () -> Unit
) {
    val context = LocalContext.current
    var scoreText by rememberSaveable { mutableStateOf("") }
    var errorText by rememberSaveable { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Set Score")
                Text(text = "Set Desired Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Enter the target score for the game:", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = scoreText,
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() }) {
                            scoreText = newText
                            val score = newText.toIntOrNull()
                            errorText = if (score != null && score > 800) {
                                "Score cannot be greater than 800"
                            } else {
                                null
                            }
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = { Text("Default: 101") },
                    isError = errorText != null
                )
                if (errorText != null) {
                    Text(text = errorText!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    scoreText.toIntOrNull()?.let { score ->
                        if (score <= 800) {
                            onSubmit(score)
                            onDismiss()
                            context.startActivity(Intent(context, Game::class.java).apply {
                                putExtra("score", score) // Fixed lowercase key
                            })
                        }
                    }
                },
                modifier = Modifier.padding(8.dp),
                enabled = errorText == null
            ) {
                Text("Set Score")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onUseDefault()
                    onSubmit(101)
                    onDismiss()
                    context.startActivity(Intent(context, Game::class.java).apply {
                        putExtra("score", 101)
                    })
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Use default: 101")
            }
        }
    )
}
