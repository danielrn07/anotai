package com.danielnascimento.anotai.data.application

import android.app.Application
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.repository.CategoryRepository

class AnotaiApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { CategoryRepository(database.categoryDao()) }
}