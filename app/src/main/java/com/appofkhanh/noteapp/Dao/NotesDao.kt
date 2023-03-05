package com.appofkhanh.noteapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.appofkhanh.noteapp.ui.Model.Notes

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 3")
    fun getUnfinishedNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 2")
    fun getDoingNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority = 1")
    fun getDoneNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    suspend fun deleteNotes(id: Int)

    @Update
    suspend fun updateNotes(notes: Notes)

}