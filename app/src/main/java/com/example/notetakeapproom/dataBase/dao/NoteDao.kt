package com.example.notetakeapproom.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notetakeapproom.dataBase.entity.Note

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note : Note)

    @Delete
    fun deleteNote(note: Note)

    @Query(value = "Select * FROM notes")
    fun getNotes():List<Note>

    @Query(value = "SELECT * FROM notes WHERE id= :id")
    fun getNoteById(id: Int): Note

    @Query(value = "SELECT * FROM notes WHERE fav= :fav")
    fun getFavNotes(fav: Int): List<Note>

    @Query(value = "UPDATE notes SET fav= :fav WHERE id= :id")
    fun updateFavNotes(fav: Int, id: Int)
}