package com.danielnascimento.anotai.presentation.viewmodel

import android.content.Context
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.danielnascimento.anotai.R
import com.danielnascimento.anotai.data.db.AppDatabase
import com.danielnascimento.anotai.data.db.entity.NoteEntity
import com.danielnascimento.anotai.data.db.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _noteList = MutableLiveData<List<NoteEntity>>()
    val noteList: LiveData<List<NoteEntity>> = _noteList

    private val _colorObservable = MutableLiveData<Int>()
    val colorObservable: LiveData<Int> = _colorObservable

    var color = ""

    init {
        _colorObservable.value = R.color.white
    }

    fun updateColor(newColor: Int) {
        _colorObservable.value = newColor
    }

    fun getAllNotes() = viewModelScope.launch {
        try {
            repository.getAllNotes().collect { notes ->
                _noteList.value = notes
            }
        } catch (e: Exception) {

        }
    }

    fun insertNote(noteEntity: NoteEntity) = viewModelScope.launch {
        try {
            repository.insertNote(noteEntity)
        } catch (e: Exception) {

        }
    }

    fun deleteNote(id: Long) = viewModelScope.launch {
        try {
            repository.deleteNote(id)
        } catch (e: Exception) {

        }
    }
}

class NoteViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {

            val database = AppDatabase.getDatabase(context)
            val repository = NoteRepository(database.noteDao())

            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}