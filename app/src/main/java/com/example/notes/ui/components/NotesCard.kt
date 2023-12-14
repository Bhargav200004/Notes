package com.example.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notes.domain.model.Note

@Composable
fun NotesCard(
    modifier : Modifier = Modifier,
    note: Note,
    onDelete : () -> Unit,
    onEdit : (id : Int) -> Unit,
    isEnable : Boolean = false,
) {

    var contentEnable by rememberSaveable { mutableStateOf(isEnable) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp, horizontal = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(7.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = note.title, style = MaterialTheme.typography.titleLarge)
                Row {
                    IconButton(onClick = { note.id?.let { onEdit(it) } }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit notes"
                        )
                    }
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete notes"
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            if (contentEnable) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = note.content,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
            }

            IconButton(onClick = { contentEnable = !contentEnable }, modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
            ) {
                Icon(
                    imageVector = if (!contentEnable) Icons.Default.ArrowDropDown
                    else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Arrow up and down",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
