package com.example.room_noteapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room_noteapp.adaptors.NoteAdaptor

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var notesAdapter:NoteAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        noteViewModel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(NoteViewModel::class.java)

        recyclerView=findViewById(R.id.recycler_view)
        notesAdapter= NoteAdaptor()
        recyclerView.adapter=notesAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this){
            //Here we can add data to our recycler view
            notesAdapter.setNotes(it)
        }
    }
}