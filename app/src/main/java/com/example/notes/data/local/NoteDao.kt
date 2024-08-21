package com.example.notes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Query("DELETE FROM NOTE WHERE id=:noteId")
    suspend fun deleteNote(noteId: Int)

    @Query("SELECT * FROM NOTE WHERE id=:id")
    suspend fun getNoteById(id : Int) : Note?

    @Query("SELECT * FROM NOTE")
    fun getAllNotes() : Flow<List<Note>>

}