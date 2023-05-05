package com.android.roomdbtest.domain.use_cases

import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository

class GetNoteByIdUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}
