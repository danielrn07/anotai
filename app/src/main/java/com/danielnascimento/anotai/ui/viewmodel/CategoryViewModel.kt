package com.danielnascimento.anotai.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryEntity>>()
    val categoryList: MutableLiveData<List<CategoryEntity>> = _categoryList

    private val _categoryStateMessage = MutableLiveData<Int>()
    val categoryStateMessage: LiveData<Int> = _categoryStateMessage

    fun getAllCategories() = viewModelScope.launch {
        _categoryList.postValue(repository.getAllCategories())
    }

    fun insertCategory(categoryEntity: CategoryEntity) = viewModelScope.launch {
        try {
            repository.insertCategory(categoryEntity)
            getAllCategories()
        } catch (e: Exception) {
            _categoryStateMessage.postValue(R.string.generic_error)
        }
    }

    fun deleteCategory(id: Long) = viewModelScope.launch {
        try {
            repository.deleteCategory(id)
            getAllCategories()
            _categoryStateMessage.postValue(R.string.success_in_deleting_category)
        } catch (e: Exception) {
            _categoryStateMessage.postValue(R.string.error_deleting_category)
        }
    }
}