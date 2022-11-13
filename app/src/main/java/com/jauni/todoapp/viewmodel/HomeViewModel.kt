package com.jauni.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import com.jauni.todoapp.data.repository.NoteRepository
import com.jauni.todoapp.model.NoteEntity
import kotlinx.coroutines.flow.Flow

interface HomeViewModelAbstract{
    val notelistFlow: Flow<List<NoteEntity>>
    fun addNote(note: NoteEntity)
    fun updateNote(note: NoteEntity)
    fun deleteNote(note: NoteEntity)
}
class HomeViewModel
constructor(
    private val noteRepository: NoteRepository,
):ViewModel(),HomeViewModelAbstract {
    override val notelistFlow: Flow<List<NoteEntity>> = noteRepository.getAllFlow()


    override fun addNote(note: NoteEntity) = noteRepository.insert(note = note)

    override fun updateNote(note: NoteEntity) = noteRepository.update(note = note)

    override fun deleteNote(note: NoteEntity) = noteRepository.delete(note=note)
}