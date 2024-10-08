package com.example.notes.data.repository

import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(
    private val noteDao : NoteDao
) : NoteRepository {
    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note= note)
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note = note)
    }

    override suspend fun deleteNote(noteId: Int) {
        noteDao.deleteNote(noteId = noteId)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id = id)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }
}