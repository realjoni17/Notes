package com.android.roomdbtest.domain.usecase

import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(userId: String, query: String): Flow<List<Note>> {
        return repository.searchNotes(userId, query)
    }
}