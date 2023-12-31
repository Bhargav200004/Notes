package com.example.notes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Upsert
    suspend fun upsertNote(note: Note)

    @Query("DELETE FROM NOTE WHERE id=:noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("SELECT * FROM NOTE WHERE id=:id")
    suspend fun getNoteById(id : Int) : Note?

    @Query("SELECT * FROM NOTE")
    fun getAllNotes() : Flow<List<Note>>

}