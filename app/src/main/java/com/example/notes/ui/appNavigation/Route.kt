package com.example.notes.ui.appNavigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {


    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object NoteScreen : Route()

    @Serializable
    data class EditScreen(val id : String?) : Route()
}