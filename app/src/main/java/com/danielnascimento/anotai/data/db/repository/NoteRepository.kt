package com.danielnascimento.anotai.data.db.repository

import com.danielnascimento.anotai.data.db.dao.NoteDao
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepository(private val noteDao: NoteDao) {
    suspend fun getAllNotes(): Flow<List<NoteEntity>> = withContext(Dispatchers.IO) {
        noteDao.getAllNotes()
    }

    suspend fun insertNote(noteEntity: NoteEntity): Long = withContext(Dispatchers.IO) {
        noteDao.insertNote(noteEntity)
    }

    suspend fun deleteNote(id: Long) = withContext(Dispatchers.IO) {
        noteDao.deleteNote(id)
    }
}