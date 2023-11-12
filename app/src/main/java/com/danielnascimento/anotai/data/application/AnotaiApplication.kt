package com.danielnascimento.anotai.data.application

import android.app.Application
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.repository.CategoryRepository
import com.danielnascimento.anotai.data.db.repository.NoteRepository

class AnotaiApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val categoryRepository by lazy { CategoryRepository(database.categoryDao()) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }
}