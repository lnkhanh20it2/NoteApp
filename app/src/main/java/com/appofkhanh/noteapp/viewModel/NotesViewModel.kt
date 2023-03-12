package com.appofkhanh.noteapp.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.appofkhanh.noteapp.database.NotesDatabase
import com.appofkhanh.noteapp.repository.NoteRepository
import com.appofkhanh.noteapp.ui.model.Notes
import com.appofkhanh.noteapp.repository.NotesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val property = MutableStateFlow("")
    private val query = MutableStateFlow("")
    private val dbNotes: Flow<List<Notes>>
    val notes: LiveData<List<Notes>>

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepositoryImpl(dao)
        dbNotes = repository.getAllNotes().asFlow()
        notes = combine(dbNotes, property, query) { notes, pr, query ->
            if (pr.isBlank() && query.isBlank()) return@combine notes
            notes.filter {
                if (pr.isBlank()) {
                    it.title.contains(query) || it.subTitle.contains(query)
                } else if (query.isBlank()) {
                    it.priority == pr
                } else it.priority == pr && it.title.contains(query) || it.subTitle.contains(query)
            }
        }.asLiveData()
    }

    fun notesFiltering(newText: String) {
        viewModelScope.launch {
            query.emit(newText)
        }
    }

    fun insertNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNotes(notes)
    }

    fun deleteNotes(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(id)
    }

    fun setProperty(temp: String) {
        viewModelScope.launch {
            property.emit(temp)
        }
    }

    fun updateNotes(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNotes(notes)
    }
}
