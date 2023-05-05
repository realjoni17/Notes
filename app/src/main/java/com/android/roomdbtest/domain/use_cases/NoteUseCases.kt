package com.android.roomdbtest.domain.use_cases

data class NoteUseCases(
    val getNotesUseCase: GetNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
)