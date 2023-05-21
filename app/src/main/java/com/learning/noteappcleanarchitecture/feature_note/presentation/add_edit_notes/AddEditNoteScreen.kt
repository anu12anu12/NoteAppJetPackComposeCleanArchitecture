package com.learning.noteappcleanarchitecture.feature_note.presentation.add_edit_notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.learning.noteappcleanarchitecture.feature_note.presentation.add_edit_notes.components.TransparentHintTextField

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val title = viewModel.notesTextTitleState.value
    val content = viewModel.notesDescriptionState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNotes -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Save")
        }
    }, scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {

            TransparentHintTextField(
                text = title.text,
                hint = title.hint,
                singleLine = true,
                modifier = Modifier.padding(16.dp),
                isHintVisible = title.isHintVisible,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                }, onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                })

            TransparentHintTextField(
                text = content.text,
                hint = content.hint,
                singleLine = false,
                modifier = Modifier.padding(16.dp),
                isHintVisible = content.isHintVisible,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                }, onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                })
        }
    }
}