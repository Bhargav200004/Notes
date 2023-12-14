package com.example.notes.ui.notesSceen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.model.Note
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
            }
            catch (e:Exception){
                Log.e("saveError","${e.message}")
            }
        }
    }


}