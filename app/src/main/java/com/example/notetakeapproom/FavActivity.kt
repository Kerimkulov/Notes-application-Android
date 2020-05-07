package com.example.notetakeapproom

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_fav.*
import kotlinx.android.synthetic.main.activity_main.*

class FavActivity : AppCompatActivity() {
    companion object{
        const val DELETE_FAV_NOTE = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)
    }

    override fun onResume() {
        super.onResume()
        initFavNote()
        goBack()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == DELETE_FAV_NOTE){
            if (resultCode == Activity.RESULT_OK){
                Log.d("del", "DELETED")
                val position = data?.getIntExtra(NoteDetailActivity.EXTRA_ID, 0)

                position?.let {
                    Log.d("taag", "YESSSSSSSSSSSSS")
                    AsyncTask.execute {
                        val note = AppDatabase.getInstance(applicationContext)?.getNoteDao()?.getNoteById(position)
                        if (note != null) {
                            AppDatabase.getInstance(applicationContext)?.getNoteDao()?.deleteNote(note)
                        }
                    }
                }
            }
        }
    }
    private fun initFavNote(){
        fav_note_list.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val notes = AppDatabase.getInstance(applicationContext)
                ?.getNoteDao()?.getFavNotes(1)

            runOnUiThread{
                fav_note_list.adapter = notes?.let {
                    NoteAdapter(notes = it, onNoteClick = {
                        val intent = Intent(this, NoteDetailActivity::class.java)
                        intent.putExtra(NoteDetailActivity.EXTRA_ID, it.id)
                        intent.putExtra(NoteDetailActivity.EXTRA_TITLE, it.name)
                        intent.putExtra(NoteDetailActivity.EXTRA_DATE, it.currDate)
                        intent.putExtra(NoteDetailActivity.EXTRA_TEXT, it.dec)
                        startActivityForResult(intent, DELETE_FAV_NOTE)
                        Log.d("ccclick", it.name)

                    })
                }
            }
        }
        designRecyclerView()
    }
    private fun designRecyclerView(){
        (fav_note_list.layoutManager as LinearLayoutManager).reverseLayout = true
        (fav_note_list.layoutManager as LinearLayoutManager).stackFromEnd = true
        val mDividerItemDecoration = DividerItemDecoration(
            fav_note_list.context,
            (fav_note_list.layoutManager as LinearLayoutManager).getOrientation()
        )
        fav_note_list.addItemDecoration(mDividerItemDecoration)
    }

    private fun goBack(){
        back_to_notes.setOnClickListener{
            finish()
        }
    }
}
