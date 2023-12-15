package com.example.notes.ui.mainScreen

import com.example.notes.domain.model.Note

data class HomeScreenState(
    val id : Int? = null,
    val title : String = "",
    val content : String = "",
    val notes: List<Note> = emptyList(),
    val note : Note? =null
)