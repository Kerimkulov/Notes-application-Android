package com.example.notetakeapproom

import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.notetakeapproom.dataBase.entity.Note
import kotlinx.android.synthetic.main.activity_make_note.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class MakeNoteActivity : AppCompatActivity() {
    private var formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_note)

        newNotes()
        back_to_notes.setOnClickListener{
            finish()
        }
    }

    private fun newNotes(){

        var curDate = LocalDateTime.now()
        var date =  curDate.format(formatter)
        save_btn_notes.setOnClickListener {
            AsyncTask.execute {
                AppDatabase.getInstance(applicationContext)
                    ?.getNoteDao()?.insertNote(
                        Note(
                        name = note_title.text.toString(), dec = note_description.text.toString(), currDate = date
                    ))
                runOnUiThread{

                    finish()
                }
            }
        }
    }
}
