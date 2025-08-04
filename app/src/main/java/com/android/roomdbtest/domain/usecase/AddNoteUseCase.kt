package com.android.roomdbtest.domain.usecase

import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import java.util.Date
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(title: String, content: String, userId: String): Long {
        val note = Note(
            title = title,
            content = content,
            userId = userId,
            timestamp = Date().time
        )
        return repository.insertNote(note)
    }
}