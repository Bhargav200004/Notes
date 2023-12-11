package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.data.local.AppDatabase
import com.example.notes.data.local.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideRoomDatabase(
        application: Application
    ) : AppDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = AppDatabase::class.java,
            name = "Note.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideNote(database: AppDatabase) : NoteDao {
        return database.NoteDao()
    }

}