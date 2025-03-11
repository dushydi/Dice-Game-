package com.example.dicegamecw.uiScreens
import AboutPopUp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.dicegamecw.R
import com.example.dicegamecw.SetValue

@Composable
fun FirstDisplay() {
    var showDialog by remember { mutableStateOf(false) }
    var showSetValueDialog by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Text (text = " WELCOME TO DICE GAME ",
             color = Color.Black,
             textDecoration = TextDecoration.Underline,
             textAlign = TextAlign.Center,
             modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(vertical = 100.dp),
             fontWeight = FontWeight.Bold,
             fontFamily= FontFamily.Serif,


        )
        Box (modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
            ){
        Image(painter = painterResource(id = R.drawable.cube),
            contentDescription = "cubes",
            modifier = Modifier.size(400.dp))
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // About Button
                Button(onClick = { showDialog = true }) {
                    Text(text = "About")
                }

                // New Button
                Button(onClick = { showSetValueDialog=true}) {
                    Text(text = "New game")
                }
            }
        }
    }

    if (showDialog) {
        AboutPopUp(onDismiss = { showDialog = false })
    }
    if (showSetValueDialog){
        SetValue(
            onDismiss = {
                showSetValueDialog = false
            },
            onSubmit = {Score -> println ("New game started with score targer :$Score")
            },
            onUseDefault = {println("Using 101")}
        )
    }
}
