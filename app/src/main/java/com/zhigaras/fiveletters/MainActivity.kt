package com.zhigaras.fiveletters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhigaras.fiveletters.ui.theme.FiveLettersTheme
import com.zhigaras.fiveletters.ui.theme.gray
import com.zhigaras.fiveletters.ui.theme.yellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiveLettersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Letter(letter = 'A')
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Letter(
    modifier: Modifier = Modifier,
    letter: Char
) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        border = BorderStroke(width = 2.dp, color = yellow),
        colors = CardDefaults.cardColors(containerColor = gray),
        modifier = modifier
            .width(70.dp)
            .padding(horizontal = 2.dp)
    ) {
        Text(
            text = letter.toString().uppercase(),
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun LetterField(
    modifier: Modifier = Modifier,
    letters: List<List<Char>>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        letters.forEach { letterRow ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                letterRow.forEach {
                    Letter(letter = it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LetterPreview() {
    FiveLettersTheme {
        Letter(letter = 'a')
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LetterFieldPreview() {
    val row = listOf('a', 'b', 'c', 'ы', 'w')
    val field = emptyList<List<Char>>().toMutableList()
    repeat(6) {
        field.add(row)
    }
    FiveLettersTheme {
        LetterField(letters = field)
    }
}