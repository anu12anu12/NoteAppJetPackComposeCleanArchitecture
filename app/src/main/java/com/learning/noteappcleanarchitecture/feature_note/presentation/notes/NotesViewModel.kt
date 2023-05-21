package com.learning.noteappcleanarchitecture.feature_note.presentation.notes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.noteappcleanarchitecture.feature_note.domain.model.Note
import com.learning.noteappcleanarchitecture.feature_note.domain.use_case.NoteUseCases
import com.learning.noteappcleanarchitecture.feature_note.domain.util.NoteOrder
import com.learning.noteappcleanarchitecture.feature_note.domain.util.OrderType
import com.learning.noteappcleanarchitecture.feature_note.presentation.notes.NotesEvent
import com.learning.noteappcleanarchitecture.feature_note.presentation.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesUseCases: NoteUseCases): ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state = _state
    private var recentlyDeletedNote: Note? = null
    private val uiEvent = MutableSharedFlow<NotesEvent>()
    private val _uiEvent = uiEvent.asSharedFlow()

    private var job: Job? = null
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent (event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class  == event.noteOrder::class &&
                   state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        job?.cancel()
        job = notesUseCases.getNotes (noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
            }
            .launchIn(viewModelScope)
    }
}