package com.appofkhanh.noteapp.repository

import androidx.lifecycle.LiveData
import com.appofkhanh.noteapp.dao.NotesDao
import com.appofkhanh.noteapp.ui.model.Notes

class NotesRepositoryImpl(private val dao: NotesDao) : NoteRepository {
    override fun getAllNotes(): LiveData<List<Notes>> = dao.getAllNotes()

    override fun getUnfinishedNotes(): LiveData<List<Notes>> = dao.getUnfinishedNotes()

    override fun getDoingNotes(): LiveData<List<Notes>> = dao.getDoingNotes()

    override fun getDoneNotes(): LiveData<List<Notes>> = dao.getDoneNotes()

    override suspend fun insertNotes(notes: Notes) {
        dao.insertNotes(notes)
    }

    override suspend fun deleteNotes(id: Int) {
        dao.deleteNotes(id)
    }

    override suspend fun updateNotes(notes: Notes) {
        dao.updateNotes(notes)
    }
}