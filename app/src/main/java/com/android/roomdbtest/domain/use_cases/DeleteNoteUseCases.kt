package com.android.roomdbtest.domain.use_cases

import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}