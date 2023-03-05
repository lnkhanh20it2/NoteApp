package com.appofkhanh.noteapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appofkhanh.noteapp.Dao.NotesDao
import com.appofkhanh.noteapp.ui.Model.Notes
import com.appofkhanh.noteapp.utilities.DATABASE_NAME

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun myNotesDao(): NotesDao

    companion object {
        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance

                return instance
            }
        }

    }
}