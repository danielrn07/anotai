package com.danielnascimento.anotai.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val _categoryList = MutableLiveData<List<CategoryEntity>>()
    val categoryList: MutableLiveData<List<CategoryEntity>> = _categoryList

    fun getAllCategories() = viewModelScope.launch {
        _categoryList.postValue(repository.getAllCategories())
    }

    fun insertCategory(categoryEntity: CategoryEntity) = viewModelScope.launch {
        repository.insertCategory(categoryEntity)
    }

    fun deleteCategory(id: Long) = viewModelScope.launch {
        repository.deleteCategory(id)
        getAllCategories()
    }
}