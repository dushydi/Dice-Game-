package com.example.dicegamecw
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Game : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetScore = intent.getIntExtra("score", 101) // Fixed lowercase key

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GameScreen(targetScore)
            }
        }
    }
}

@Composable
fun GameScreen(targetScore: Int) {
    var humanScore by rememberSaveable { mutableStateOf(0) }
    var computerScore by rememberSaveable { mutableStateOf(0) }

    var humanDiceValues by rememberSaveable { mutableStateOf(rollDice()) }
    var computerDiceValues by rememberSaveable { mutableStateOf(rollDice()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Score: H:$humanScore / C:$computerScore",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Computer",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            DiceGridDisplay(diceValues = computerDiceValues)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Human",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            DiceGridDisplay(diceValues = humanDiceValues)

            Spacer(modifier = Modifier.weight(1f))

            // Buttons placed inside a Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    humanDiceValues = rollDice()
                }) {
                    Text("Roll Dice")
                }

                Button(onClick = {
                    humanDiceValues = rollDice()
                }) {
                    Text("Re Roll")
                }

                Button(onClick = {
                    humanDiceValues = rollDice()
                }) {
                    Text("Score")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun DiceGridDisplay(diceValues: List<Int>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp), // Prevent layout issues
        horizontalArrangement = Arrangement.Center
    ) {
        items(diceValues.size) { index ->
            val diceImage = when (diceValues[index]) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                6 -> R.drawable.dice_6
                else -> R.drawable.dice_1
            }
            Image(
                painter = painterResource(id = diceImage),
                contentDescription = "Dice ${diceValues[index]}",
                modifier = Modifier
                    .padding(4.dp)
                    .size(60.dp)
            )
        }
    }
}

fun rollDice(): List<Int> {
    return List(5) { (1..6).random() }
}
