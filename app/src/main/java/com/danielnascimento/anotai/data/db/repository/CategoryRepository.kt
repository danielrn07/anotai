package com.danielnascimento.anotai.data.db.repository

import com.danielnascimento.anotai.data.db.dao.CategoryDao
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun getAllCategories(): Flow<List<CategoryEntity>> = withContext(Dispatchers.IO) {
        categoryDao.getAllCategories()
    }

    suspend fun insertCategory(categoryEntity: CategoryEntity): Long = withContext(Dispatchers.IO) {
        categoryDao.insertCategory(categoryEntity)
    }

    suspend fun deleteCategory(id: Long) = withContext(Dispatchers.IO) {
        categoryDao.deleteCategory(id)
    }
}