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
) : ViewModel(){

    init {
        fetchAllNotes()
    }



    private val _state = MutableStateFlow(HomeScreenState())
    val state  = combine(
        _state,
        noteRepository.getAllNotes()
    ){state , notes ->
        state.copy(
            notes = notes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000 ),
        initialValue = HomeScreenState()
    )


    fun onEvent(event : HomeScreenEvent){
        when (event){
            is HomeScreenEvent.OnTitleChange -> {
                _state.update {
                    it.copy(title = event.title)
                }
            }
            is HomeScreenEvent.OnContentChange -> {
                _state.update {
                    it.copy(content = event.content)
                }
            }
            is HomeScreenEvent.GetNoteById -> {
            }
            HomeScreenEvent.DeleteNote -> deleteNote()
            HomeScreenEvent.SaveUpdateNote -> saveNote()
        }
    }



    private fun saveNote() {
        viewModelScope.launch {
            state.value.note?.let {note ->
                noteRepository.upsertNote(note = note)
            }
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            state.value.note?.let { note->
                noteRepository.deleteNote(note= note)
            }
        }
    }

    private fun fetchAllNotes() {
        viewModelScope.launch {
            try {
                noteRepository.getAllNotes().let {
                    _state.update {note->
                        note.copy(
                            title = note.title,
                            content = note.content
                        )
                    }
                }
            }
            catch (e : Exception){
                Log.e("getnotes" , "${e.message}")
            }
        }
    }


}