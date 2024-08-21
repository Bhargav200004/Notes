package com.example.notes.domain.repository

import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun updateNote(note : Note)

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(noteId: Int)

    suspend fun getNoteById(id : Int) : Note?

    fun getAllNotes() : Flow<List<Note>>

}