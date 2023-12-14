package com.example.notes.ui.notesSceen

import com.example.notes.domain.model.Note

data class NotesScreenStates(
    val id : Int? = null,
    val title : String = "",
    val content : String = "",
    val note: List<Note> = emptyList()
)