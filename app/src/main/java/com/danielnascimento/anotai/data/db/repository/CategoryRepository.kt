package com.danielnascimento.anotai.data.db.repository

import com.danielnascimento.anotai.data.db.dao.CategoryDao
import com.danielnascimento.anotai.data.db.entity.CategoryEntity

class CategoryRepository(private val categoryDao: CategoryDao) {
    suspend fun getAllCategories(): List<CategoryEntity> {
        return categoryDao.getAllCategories()
    }

    suspend fun insertCategory(categoryEntity: CategoryEntity): Long {
        return categoryDao.insertCategory(categoryEntity)
    }
}