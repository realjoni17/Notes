package com.jauni.todoapp.data.repository

import com.jauni.todoapp.data.NoteDao
import com.jauni.todoapp.model.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao
){
     fun getAllFlow() : Flow<List<NoteEntity>> = noteDao.getAll()
     fun insert(note: NoteEntity) = noteDao.insert(note = note)
     fun update(note: NoteEntity) = noteDao.update(note = note)
     fun delete(note: NoteEntity) = noteDao.delete(note=note)
}