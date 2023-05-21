package com.learning.noteappcleanarchitecture.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.learning.noteappcleanarchitecture.ui.theme.Typography


@Composable
fun NoteItem(
    modifier: Modifier,
    title: String,
    content: String,
    onDeletedClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = Typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        Spacer(
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = content,
            style = Typography.body1,
            color = MaterialTheme.colors.surface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.BottomEnd // Align content to the bottom end
        ) {
            IconButton(
                onClick = onDeletedClicked,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}