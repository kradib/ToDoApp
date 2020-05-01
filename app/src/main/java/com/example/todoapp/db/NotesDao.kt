package com.example.todoapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao{
    @Query("select * from notesData")
    fun getAll(): List<Note>
    @Insert(onConflict = REPLACE)
    fun insert(notes: Note)
    @Update
    fun updateNotes(notes: Note)
    @Delete
    fun deleteNotes(notes:Note)
}