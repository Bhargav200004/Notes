package com.example.notes.domain.repository

import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun upsertNote(note : Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id : Int) : Note?

    fun getAllNotes() : Flow<List<Note>>

}