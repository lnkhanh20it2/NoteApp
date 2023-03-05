package com.appofkhanh.noteapp.Repository

import androidx.lifecycle.LiveData
import com.appofkhanh.noteapp.Dao.NotesDao
import com.appofkhanh.noteapp.ui.Model.Notes

class NotesRepository(private val dao: NotesDao) {

    val allNotes: LiveData<List<Notes>> = dao.getAllNotes()

    fun getUnfinishedNotes(): LiveData<List<Notes>> = dao.getUnfinishedNotes()

    fun getDoingNotes(): LiveData<List<Notes>> = dao.getDoingNotes()

    fun getDoneNotes(): LiveData<List<Notes>> = dao.getDoneNotes()

    suspend fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    suspend fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }

    suspend fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }


}