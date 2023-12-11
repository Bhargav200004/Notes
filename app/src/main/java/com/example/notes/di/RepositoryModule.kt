package com.example.notes.di

import com.example.notes.data.repository.NoteRepositoryImp
import com.example.notes.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNoteRepository(
        noteRepositoryImp: NoteRepositoryImp
    ) : NoteRepository

}