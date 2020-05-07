package com.example.notetakeapproom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notetakeapproom.dataBase.dao.NoteDao
import com.example.notetakeapproom.dataBase.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun getNoteDao(): NoteDao
    companion object{
        private const val DB_NAME = "notes.db"
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(context,
                AppDatabase::class.java, DB_NAME).build()
            }
            return instance
        }
    }
}