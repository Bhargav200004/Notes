package com.example.notes.ui.notesSceen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.notes.ui.components.NoteAdd

@Composable
fun NoteScreen() {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            NoteScreenTopBar(
                onSave = {},
                onBackButton = {}
            )
        }
    ) {paddingValues ->  
        
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
        ) {
            NoteAdd(
                title = title,
                content = content,
                onTitleChange = { title = it },
                onContentChange = {content = it}
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteScreenTopBar(
    onSave : () -> Unit,
    onBackButton : () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Notes" , style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = { onBackButton() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Button Click"
                )
            }
        },
        actions = {
            IconButton(onClick = { onSave() }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "On Save"
                )
            }
        }
    )
}
