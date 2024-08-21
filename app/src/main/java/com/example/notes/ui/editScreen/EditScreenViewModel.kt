package com.example.notes.ui.editScreen

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import com.example.notes.ui.appNavigation.Route
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
class EditScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    init {
        val id = savedStateHandle.toRoute<Route.EditScreen>().id
        viewModelScope.launch {
            if (id != null) {
                noteRepository.getNoteById(id = id.toInt())?.let {note ->
                    _state.update { homeScreenState ->
                        homeScreenState.copy(
                            id = note.id,
                            title = note.title,
                            content = note.content
                        )
                    }
                }
            }
        }
    }

    private val _state = MutableStateFlow(EditScreenStates())
    val state = combine(
        _state,
        noteRepository.getAllNotes()
    ){state , notes ->
    state.copy(
        note = notes
    ) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EditScreenStates()
    )



    //SnackBar
    private val _snackBarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event : EditScreenEvent){
        when(event){
            is EditScreenEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }
            is EditScreenEvent.OnContentChange -> {
                _state.update {
                    it.copy(
                        content = event.content
                    )
                }
            }
            EditScreenEvent.EditNote -> state.value.id?.let { editNote(id = it) }
            EditScreenEvent.DeleteNote -> TODO()
            EditScreenEvent.IsCompleteEvent -> TODO()
        }
    }

    private fun editNote(id : Int) {
        viewModelScope.launch {
            val state = _state.value
            try {
                if (state.title != "" && state.content !="" ) {
                    noteRepository.updateNote(
                        note = Note(
                            id = id,
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
                else{
                    _snackBarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            message = "Please enter blank part",
                            duration = SnackbarDuration.Long
                        )
                    )
                }
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