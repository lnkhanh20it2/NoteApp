package com.appofkhanh.noteapp.repository

import androidx.lifecycle.LiveData
import com.appofkhanh.noteapp.ui.model.Notes

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Notes>>
    fun getUnfinishedNotes(): LiveData<List<Notes>>
    fun getDoingNotes(): LiveData<List<Notes>>
    fun getDoneNotes(): LiveData<List<Notes>>
    suspend fun insertNotes(notes: Notes)
    suspend fun deleteNotes(id: Int)
    suspend fun updateNotes(notes: Notes)
}
