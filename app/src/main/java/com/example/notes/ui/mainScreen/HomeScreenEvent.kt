package com.example.notes.ui.mainScreen

sealed class HomeScreenEvent {

    data class OnTitleChange (val title : String) : HomeScreenEvent()
    data class OnContentChange (val content : String) : HomeScreenEvent()
    data object SaveUpdateNote : HomeScreenEvent()
    data object DeleteNote : HomeScreenEvent()
    data class GetNoteById (val id : Int) : HomeScreenEvent()
}