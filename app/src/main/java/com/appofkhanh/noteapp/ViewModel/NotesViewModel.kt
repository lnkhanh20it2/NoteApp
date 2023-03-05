package com.appofkhanh.noteapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.appofkhanh.noteapp.Database.NotesDatabase
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.Repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun getUnfinishedNotes(): LiveData<List<Notes>> = repository.getUnfinishedNotes()

    fun getDoingNotes(): LiveData<List<Notes>> = repository.getDoingNotes()

    fun getDoneNotes(): LiveData<List<Notes>> = repository.getDoneNotes()

    fun insertNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNotes(notes)
    }

    fun getNotes():LiveData<List<Notes>> = repository.allNotes

}