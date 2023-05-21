package com.learning.noteappcleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.learning.noteappcleanarchitecture.feature_note.data.data_source.NoteDatabase
import com.learning.noteappcleanarchitecture.feature_note.data.repository.NoteRepositoryImpl
import com.learning.noteappcleanarchitecture.feature_note.domain.repository.NoteRepository
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.AddNote
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.DeleteNote
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.GetNotes
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context,
                NoteDatabase::class.java,
                NoteDatabase.DATABASE_NAME
        )
        .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository)
        )
    }
}