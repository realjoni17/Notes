package com.android.roomdbtest.domain.use_cases

import com.android.roomdbtest.domain.model.InvalidNoteException
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository

class InsertNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Note title can't be null. Error Code:1")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Note content can't be null. Error Code:2")
        }
        repository.insertNote(note)
    }
}