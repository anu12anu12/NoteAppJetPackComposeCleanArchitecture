package com.learning.noteappcleanarchitecture.feature_note.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.learning.noteappcleanarchitecture.R
import com.learning.noteappcleanarchitecture.feature_note.presentation.notes.components.NoteItem
import com.learning.noteappcleanarchitecture.feature_note.presentation.notes.components.OrderSection
import com.learning.noteappcleanarchitecture.feature_note.presentation.util.Screen
import com.learning.noteappcleanarchitecture.ui.theme.RedOrange
import com.learning.noteappcleanarchitecture.ui.theme.Typography
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    val state = notesViewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddNotesScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    text = stringResource(id = R.string.str_notes),
                    color = MaterialTheme.colors.primary,
                    style = Typography.h6
                )
                
                IconButton(onClick = {
                    notesViewModel.onEvent(NotesEvent.ToggleOrderSection)
                }) {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier.fillMaxWidth(),
                    noteOrder = state.noteOrder,
                    onOrderChanged = {
                        notesViewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
            ) {
                items(state.notes) {
                    NoteItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(RedOrange)
                            .clip(RoundedCornerShape(8.dp)
                            ),
                        title = it.title,
                        content = it.content,
                        onDeletedClicked = {
                            notesViewModel.onEvent(NotesEvent.DeleteNote(it))
                        },
                    )
                }
            }
        }
    }
}