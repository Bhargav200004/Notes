package com.example.notes.ui.editScreen

import com.example.notes.domain.model.Note

data class EditScreenStates(
    val id : Int? = null,
    val title : String = "",
    val content : String = "",
    val note: List<Note> = emptyList(),
)