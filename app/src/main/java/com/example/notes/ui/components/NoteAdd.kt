package com.example.notes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoteAdd(
    modifier : Modifier = Modifier ,
    title : String,
    content : String,
    onTitleChange : (String) -> Unit,
    onContentChange : (String) -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Title" ,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = { Text(text = "Tittle")},
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Content" ,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            value = content,
            onValueChange = onContentChange,
            label = { Text(text = "content")}
        )
    }
}