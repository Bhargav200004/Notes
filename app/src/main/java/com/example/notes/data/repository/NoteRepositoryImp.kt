package com.example.notes.data.repository

import com.example.notes.data.local.NoteDao
import com.example.notes.domain.model.Note
import com.example.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(
    private val noteDao : NoteDao
) : NoteRepository {
    override suspend fun upsertNote(note: Note) {
        noteDao.upsertNote(note= note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id = id)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }
}