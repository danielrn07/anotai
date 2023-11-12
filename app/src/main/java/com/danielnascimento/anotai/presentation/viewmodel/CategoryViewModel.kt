package com.danielnascimento.anotai.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryEntity>>()
    val categoryList: LiveData<List<CategoryEntity>> = _categoryList

    private val _categoryStateMessage = MutableLiveData<Int>()
    val categoryStateMessage: LiveData<Int> = _categoryStateMessage

    fun getAllCategories() = viewModelScope.launch {
        try {
            repository.getAllCategories().collect { categories ->
                _categoryList.value = categories
            }
        } catch (e: Exception) {
            _categoryStateMessage.postValue(R.string.generic_error)
        }
    }

    fun insertCategory(categoryEntity: CategoryEntity) = viewModelScope.launch {
        try {
            repository.insertCategory(categoryEntity)
        } catch (e: Exception) {
            _categoryStateMessage.postValue(R.string.generic_error)
        }
    }

    fun deleteCategory(id: Long) = viewModelScope.launch {
        try {
            repository.deleteCategory(id)
            _categoryStateMessage.postValue(R.string.success_in_deleting_category)
        } catch (e: Exception) {
            _categoryStateMessage.postValue(R.string.error_deleting_category)
        }
    }
}

class CategoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
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