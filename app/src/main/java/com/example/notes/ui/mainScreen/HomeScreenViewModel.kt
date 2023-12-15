package com.example.notes.ui.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    init {
        fetchNoteById()
    }


    private val _state = MutableStateFlow(HomeScreenState())
    val state = combine(
        _state,
        noteRepository.getAllNotes()
    ) { state, notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = HomeScreenState()
    )


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.DeleteNote -> deleteNote()
            is HomeScreenEvent.GetNoteById -> TODO()
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            try {
                val state = state.value.id
                if (state != null) {
                    noteRepository.deleteNote(state)
                }
                Log.e("state", "$state")
            } catch (e: Exception) {
                Log.e("DeleteNote", "${e.message}")
            }
        }
    }

    private fun fetchNoteById() {
        viewModelScope.launch {
            try {
//                TODO("adding note Id")
                noteRepository.getNoteById(4)?.let { note ->
                    _state.update { homeScreenState ->
                        homeScreenState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content
                        )

                    }

                }
            } catch (e: Exception) {
                Log.e("ById", "${e.message}")
            }
        }
    }


}