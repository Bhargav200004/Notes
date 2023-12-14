package com.example.notes.ui.notesSceen

sealed class NotesScreenEvent {
    data class OnTitleChange(val title: String) : NotesScreenEvent()
    data class OnContentChange(val content: String) : NotesScreenEvent()

    data object IsCompleteEvent : NotesScreenEvent()
    data object SaveNote : NotesScreenEvent()
    data object DeleteNote : NotesScreenEvent()
}