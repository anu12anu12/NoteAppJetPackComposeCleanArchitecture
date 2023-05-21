package com.learning.noteappcleanarchitecture.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.learning.noteappcleanarchitecture.ui.theme.BabyBlue
import com.learning.noteappcleanarchitecture.ui.theme.LightGreen
import com.learning.noteappcleanarchitecture.ui.theme.RedOrange
import com.learning.noteappcleanarchitecture.ui.theme.RedPink
import com.learning.noteappcleanarchitecture.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
