package com.android.roomdbtest.domain.use_cases
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow


class GetNoteUseCase (private val repository: NoteRepository)
{
operator fun invoke () : Flow<List<Note>> {
    return repository.getNotes()
}
}