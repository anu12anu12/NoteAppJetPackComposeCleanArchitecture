package com.learning.noteappcleanarchitecture.feature_note.presentation.add_edit_notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.noteappcleanarchitecture.feature_note.domain.model.Note
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val notesUseCases: NoteUseCases): ViewModel() {
    private val _notesTextTitleState = mutableStateOf(
        NoteTextFieldState(hint = "Enter title here")
    )
    private val _notesDescriptionState = mutableStateOf(
        NoteTextFieldState(hint = "Enter description here")
    )

    val notesTextTitleState = _notesTextTitleState
    val notesDescriptionState = _notesDescriptionState
    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _notesTextTitleState.value = _notesTextTitleState.value.copy(
                    text = event.title
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _notesDescriptionState.value = _notesDescriptionState.value.copy(
                    text = event.content
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _notesTextTitleState.value = _notesTextTitleState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _notesTextTitleState.value.text.isBlank()
                )

            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _notesDescriptionState.value = _notesDescriptionState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _notesDescriptionState.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(
                        Note(
                            _notesTextTitleState.value.text,
                            _notesDescriptionState.value.text,
                            System.currentTimeMillis()
                        )
                    )
                    _event.emit(UiEvent.SaveNotes)
                }
            }

        }
    }

    sealed class UiEvent {
        object SaveNotes: UiEvent()
    }
}