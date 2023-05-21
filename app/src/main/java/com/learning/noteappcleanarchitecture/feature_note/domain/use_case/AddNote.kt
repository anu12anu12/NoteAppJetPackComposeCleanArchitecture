package com.learning.noteappcleanarchitecture.feature_note.domain.use_case

import com.learning.noteappcleanarchitecture.feature_note.domain.model.Note
import com.learning.noteappcleanarchitecture.feature_note.domain.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun  invoke(note: Note) {
        noteRepository.insertNote(note)
    }
}