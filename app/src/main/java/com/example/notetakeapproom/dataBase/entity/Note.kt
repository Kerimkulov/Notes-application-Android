package com.example.notetakeapproom.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "dec")  val dec: String,
    @ColumnInfo(name = "currDate")  val currDate : String,
    @ColumnInfo(name = "fav")  val fav : Int = 0
)
