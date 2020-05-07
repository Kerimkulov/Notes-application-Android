package com.example.notetakeapproom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakeapproom.dataBase.entity.Note
import kotlinx.android.synthetic.main.layout_note.view.*

class NoteAdapter(
    private val notes: List<Note> = listOf(),
    private val onNoteClick:(Note) -> Unit
    ): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_note, parent, false)
            return NoteViewHolder(view)
        }

        override fun getItemCount(): Int {
            return notes.count()
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            holder.bindNote(notes[position])
        }
        inner class NoteViewHolder(
        private  val view: View
    ): RecyclerView.ViewHolder(view) {
        fun bindNote(note: Note){
            view.note_title_view.text = note.name
            view.note_text_view.text = note.dec
            view.note_date_view.text = note.currDate


            view.setOnClickListener{
                onNoteClick(note)
            }
        }
    }

    }