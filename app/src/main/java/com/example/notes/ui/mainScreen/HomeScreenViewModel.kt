package com.example.notes.ui.mainScreen

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.util.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

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

    //SnackBar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.DeleteNote -> deleteNote()
            is HomeScreenEvent.GetNoteById -> {
                fetchNoteById(id = event.id)
            }

            HomeScreenEvent.OnEdit -> {
                viewModelScope.launch {
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Coming Soon",
                            duration = SnackbarDuration.Long
                        )
                    )
                }
            }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            try {
                val state = state.value.id
                if (state != null) {
                    noteRepository.deleteNote(state)
                }
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Successfully delete the note",
                        duration = SnackbarDuration.Short
                    )
                )
            } catch (e: Exception) {
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't delete the note ${e.message}"
                    )
                )
            }
        }
    }

    private fun fetchNoteById(id: Int) {
        viewModelScope.launch {
            try {
                noteRepository.getNoteById(id)?.let { note ->
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