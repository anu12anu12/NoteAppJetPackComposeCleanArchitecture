package com.learning.noteappcleanarchitecture.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learning.noteappcleanarchitecture.feature_note.presentation.add_edit_notes.AddEditNoteScreen
import com.learning.noteappcleanarchitecture.feature_note.presentation.notes.NotesScreen
import com.learning.noteappcleanarchitecture.feature_note.presentation.util.Screen
import com.learning.noteappcleanarchitecture.ui.theme.NoteAppCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppCleanArchitectureTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.NotesScreen.route) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    
                    composable(Screen.AddNotesScreen.route) {
                        AddEditNoteScreen(navController = navController)
                    }
                }
            }
        }
    }

}
