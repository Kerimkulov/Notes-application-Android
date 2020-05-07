package com.example.notetakeapproom

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.notetakeapproom.dataBase.entity.Note
import kotlinx.android.synthetic.main.activity_make_note.back_to_notes
import kotlinx.android.synthetic.main.activity_note_detail.*


class NoteDetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_TITLE = "title"
        const val EXTRA_TEXT = "description"
        const val EXTRA_DATE = "date"
        const val EXTRA_OBJECT = "note"
        const val EXTRA_ID = "id"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_TEXT)
        val time = intent.getStringExtra(EXTRA_DATE)

        displayDetails(title,description,time)
        goBack()
        onNoteDelete(id)
        addFav(id)
    }
    private fun goBack(){
        back_to_notes.setOnClickListener{
            finish()
        }
    }

    private fun onNoteDelete(pos: Int) {
        delete_btn_notes.setOnClickListener{
            val intent = Intent()
            intent.putExtra(EXTRA_ID, pos)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
    private fun displayDetails(title:String, description: String, time: String){
        if(!title.isEmpty()){
            item_title.text = title
        }
        if(!description.isEmpty()){
            item_text.text = description
        }
        item_date.text = time
    }

    private fun addFav(id: Int){
        add_fav_btn.setOnClickListener{
            AsyncTask.execute {
                AppDatabase.getInstance(applicationContext)
                    ?.getNoteDao()?.updateFavNotes(1, id)

                runOnUiThread{
                    finish()
                }
            }
        }
    }
}


