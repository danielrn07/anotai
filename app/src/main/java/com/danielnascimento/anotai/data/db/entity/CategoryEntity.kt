package com.danielnascimento.anotai.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
class CategoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = ""
)