package com.example.notes.ui.notesSceen

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.Note
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
class NoteScreenViewModel @Inject constructor(
    val noteRepository: NoteRepository
): ViewModel() {

    private val _state = MutableStateFlow(NotesScreenStates())
    val state = combine(
        _state,
        noteRepository.getAllNotes()
    ){state , notes ->
    state.copy(
        note = notes
    ) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotesScreenStates()
    )



    //SnackBar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event : NotesScreenEvent){
        when(event){
            is NotesScreenEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            is NotesScreenEvent.OnContentChange -> {
                _state.update {
                    it.copy(
                        content = event.content
                    )
                }
            }
            NotesScreenEvent.SaveNote -> saveNote()
            NotesScreenEvent.DeleteNote -> TODO()
            NotesScreenEvent.IsCompleteEvent -> TODO()
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            val state = _state.value
            try {
                noteRepository.upsertNote(
                    note = Note(
                        title = state.title,
                        content = state.content
                    )
                )
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Successfully created note",
                        duration = SnackbarDuration.Short
                    )
                )
            }
            catch (e:Exception){
                _snackBarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        message = "Couldn't created ${e.message}"
                    )
                )
            }
        }
    }


}