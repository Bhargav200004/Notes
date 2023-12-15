package com.example.notes.ui.mainScreen

sealed class HomeScreenEvent {
    data object DeleteNote : HomeScreenEvent()
    data class GetNoteById (val id : Int) : HomeScreenEvent()
}