package com.example.room_noteapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room_noteapp.activities.AddEditActivity
import com.example.room_noteapp.adaptors.NoteAdaptor
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var notesAdapter:NoteAdaptor
    private lateinit var addNotebtn:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        noteViewModel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(NoteViewModel::class.java)

        addNotebtn=findViewById(R.id.floating_action_btn)
        recyclerView=findViewById(R.id.recycler_view)
        notesAdapter= NoteAdaptor()
        recyclerView.adapter=notesAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this){
            //Here we can add data to our recycler view
            notesAdapter.setNotes(it)
        }

        //Code for adding Notes
       var getResult=
           registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
               if (it.resultCode==com.example.room_noteapp.utils.Constants.REQUEST_CODE){
                   var title=it.data?.getStringExtra(com.example.room_noteapp.utils.Constants.EXTRA_TITLE)
                   var description=it.data?.getStringExtra(com.example.room_noteapp.utils.Constants.EXTRA_DESCRIPTION)
                   var priority=it.data?.getIntExtra(com.example.room_noteapp.utils.Constants.EXTRA_PRIORITY,-1)

                   val note=Note(title!!,description!!,priority!!)
                   noteViewModel.addNote(note)
               }
           }
        addNotebtn.setOnClickListener{
            val intent=Intent(this@MainActivity,AddEditActivity::class.java)
            getResult.launch(intent)
        }


    }
}