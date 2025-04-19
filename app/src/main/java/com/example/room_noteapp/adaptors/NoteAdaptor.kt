package com.example.room_noteapp.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.room_noteapp.Note
import com.example.room_noteapp.R

class NoteAdaptor():
    RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>() {
    var notesList= mutableListOf<Note>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item,parent,false)
        return NoteViewHolder(view)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notesList[position]
        holder.textViewtitle.text=note.title
        holder.textViewdescription.text=note.description
        holder.textViewpriority.text=note.priority.toString()
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
    fun setNotes(note:MutableList<Note>){
        notesList=note
    }

    inner class NoteViewHolder(view:View):RecyclerView.ViewHolder(view){
        var textViewtitle=view.findViewById(R.id.text_view_title) as TextView
        var textViewdescription: TextView = view.findViewById(R.id.text_view_description)
        var textViewpriority:TextView=view.findViewById(R.id.text_view_priority)
    }
}