package com.danielnascimento.anotai.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danielnascimento.anotai.data.db.dao.CategoryDao
import com.danielnascimento.anotai.data.db.dao.NoteDao
import com.danielnascimento.anotai.data.db.entity.CategoryEntity
import com.danielnascimento.anotai.data.db.entity.NoteEntity

@Database(entities = [CategoryEntity::class, NoteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anotai_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}