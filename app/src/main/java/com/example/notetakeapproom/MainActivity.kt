package com.example.notetakeapproom

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetakeapproom.dataBase.entity.Note
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    companion object{
        const val ADD_TASK_REQUEST = 1
        const val DELETE_TASK_REQUEST = 2
    }
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        addNewNote()
        initListNote()
        favActivity()
    }

    private fun addNewNote(){
        add_note_btn.setOnClickListener{
            val intent = Intent(this, MakeNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun favActivity(){
        go_fav_act.setOnClickListener{
            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == DELETE_TASK_REQUEST){
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
    private fun initListNote(){
       init_note_list.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val notes = AppDatabase.getInstance(applicationContext)
                ?.getNoteDao()?.getNotes()

            runOnUiThread{
                init_note_list.adapter = notes?.let {
                    NoteAdapter(notes = it, onNoteClick = {
                        val intent = Intent(this, NoteDetailActivity::class.java)
                        intent.putExtra(NoteDetailActivity.EXTRA_ID, it.id)
                        intent.putExtra(NoteDetailActivity.EXTRA_TITLE, it.name)
                        intent.putExtra(NoteDetailActivity.EXTRA_DATE, it.currDate)
                        intent.putExtra(NoteDetailActivity.EXTRA_TEXT, it.dec)
                        Log.d("ccclick", "okey")
                        startActivityForResult(intent, DELETE_TASK_REQUEST)

                    })
                }
            }
        }
        designRecyclerView()
    }

    private fun designRecyclerView(){
        (init_note_list.layoutManager as LinearLayoutManager).reverseLayout = true
        (init_note_list.layoutManager as LinearLayoutManager).stackFromEnd = true
        val mDividerItemDecoration = DividerItemDecoration(
            init_note_list.context,
            (init_note_list.layoutManager as LinearLayoutManager).getOrientation()
        )
        init_note_list.addItemDecoration(mDividerItemDecoration)
    }



}