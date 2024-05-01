package com.android.roomdbtest.mappers

import com.android.roomdbtest.data.network.dto.NotesDto
import com.android.roomdbtest.data.network.dto.NotesDtoItem
import com.android.roomdbtest.domain.model.Note

fun List<NotesDtoItem>.toDomain() : List<Note> {
    return map {
        Note(
            id = it.id,
            title = it.title,
            content = it.body
        )
    }
}