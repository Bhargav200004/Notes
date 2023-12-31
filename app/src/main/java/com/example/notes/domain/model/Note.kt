package com.example.notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val title : String,
    val content : String,
)
