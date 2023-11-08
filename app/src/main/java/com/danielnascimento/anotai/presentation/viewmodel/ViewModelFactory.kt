package com.danielnascimento.anotai.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.repository.CategoryRepository

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {

            val database = AppDatabase.getDatabase(context)

            val repository = CategoryRepository(database.categoryDao())

            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}