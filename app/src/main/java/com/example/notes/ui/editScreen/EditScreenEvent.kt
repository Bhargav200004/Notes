package com.example.notes.ui.editScreen

sealed class EditScreenEvent {
    data class OnTitleChange(val title: String) : EditScreenEvent()
    data class OnContentChange(val content: String) : EditScreenEvent()

    data object IsCompleteEvent : EditScreenEvent()
    data object EditNote : EditScreenEvent()
    data object DeleteNote : EditScreenEvent()
}