package com.learning.noteappcleanarchitecture.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.learning.noteappcleanarchitecture.feature_note.domain.util.NoteOrder
import com.learning.noteappcleanarchitecture.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChanged: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = (noteOrder is NoteOrder.Title),
                onSelect = { onOrderChanged(NoteOrder.Title(noteOrder.orderType)) },
                modifier = Modifier
            )
            DefaultRadioButton(
                text = "Date",
                selected = (noteOrder is NoteOrder.Date),
                onSelect = { onOrderChanged(NoteOrder.Date(noteOrder.orderType)) },
                modifier = Modifier
            )
            DefaultRadioButton(
                text = "Color",
                selected = (noteOrder is NoteOrder.Color),
                onSelect = { onOrderChanged(NoteOrder.Color(noteOrder.orderType)) },
                modifier = Modifier
            )
        }

        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = (noteOrder.orderType is OrderType.Ascending),
                onSelect = { onOrderChanged(noteOrder.copy(OrderType.Ascending)) },
                modifier = Modifier
            )
            DefaultRadioButton(
                text = "Descending",
                selected = (noteOrder.orderType is OrderType.Descending),
                onSelect = { onOrderChanged(noteOrder.copy(OrderType.Descending)) },
                modifier = Modifier
            )
        }
    }
}
