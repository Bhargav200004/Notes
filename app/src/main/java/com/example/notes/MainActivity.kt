package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.notes.domain.model.Note
import com.example.notes.ui.mainScreen.HomeScreen
import com.example.notes.ui.theme.NotesTheme

class MainActivity : ComponentActivity() {
    private val note = arrayListOf(
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
        Note(id = 1 , title = "day1" , content = "I am creating a Jetpack Compose Dialog that contains a Column where all of the elements should always be visible, except for the third element, which is a Text that should be scrollable if the text doesn't fit the screen space. I almost achieved this with a secondary scrollable Column just for that Text element."),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(notes = note)
                }
            }
        }
    }
}
