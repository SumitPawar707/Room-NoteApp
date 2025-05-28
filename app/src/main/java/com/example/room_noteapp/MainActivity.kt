package com.example.room_noteapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.room_noteapp.activities.AddEditActivity
import com.example.room_noteapp.adaptors.NoteAdaptor
import com.example.room_noteapp.utils.Constants
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NoteAdaptor.OnCLickListener{
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var notesAdapter:NoteAdaptor
    private lateinit var addNotebtn:FloatingActionButton
    private lateinit var getResult:ActivityResultLauncher<Intent>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        noteViewModel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(NoteViewModel::class.java)

        //Required for OptionMenu
        val toolbar: MaterialToolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)



        addNotebtn=findViewById(R.id.floating_action_btn)
        recyclerView=findViewById(R.id.recycler_view)
        notesAdapter= NoteAdaptor(this)
        recyclerView.adapter=notesAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        noteViewModel.allNotes.observe(this){
            //Here we can add data to our recycler view
            notesAdapter.setNotes(it)
        }

        //Code for adding Notes
        getResult=
           registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
               if (it.resultCode==com.example.room_noteapp.utils.Constants.REQUEST_CODE){
                   var title=it.data?.getStringExtra(com.example.room_noteapp.utils.Constants.EXTRA_TITLE)
                   var description=it.data?.getStringExtra(com.example.room_noteapp.utils.Constants.EXTRA_DESCRIPTION)
                   var priority=it.data?.getIntExtra(com.example.room_noteapp.utils.Constants.EXTRA_PRIORITY,-1)

                   val note=Note(title!!,description!!,priority!!)
                   noteViewModel.addNote(note)
               }else if (it.resultCode==Constants.EDIT_REQUEST_CODE){
                   var title=it.data?.getStringExtra(Constants.EXTRA_TITLE)
                   var description=it.data?.getStringExtra(Constants.EXTRA_DESCRIPTION)
                   var priority=it.data?.getIntExtra(Constants.EXTRA_PRIORITY,-1)
                   var id=it.data?.getIntExtra(Constants.EXTRA_ID,-1)

                   val note=Note(title!!,description!!,priority!!)
                   note.id=id!!
                   noteViewModel.updateNote(note)
               }
           }
        addNotebtn.setOnClickListener{
            val intent=Intent(this@MainActivity,AddEditActivity::class.java)
            getResult.launch(intent)
        }

        //Code for delete particular note (right or left swipe to delete)
        ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val removedNote=notesAdapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel.deleteNote(notesAdapter.getNoteAt(viewHolder.adapterPosition))

                Snackbar.make(recyclerView,"Note Deleted",Snackbar.LENGTH_LONG).setAction("UNDO"){
                    noteViewModel.addNote(removedNote)
                }.show()
            }

        }).attachToRecyclerView(recyclerView)

    }
    // Inflate the options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle option menu item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes_menu -> {
                noteViewModel.deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCLickItem(note: Note) {
        var title=note.title
        var description=note.description
        var priority=note.priority
        var id=note.id

        val intent=Intent(this@MainActivity,AddEditActivity::class.java)
        intent.putExtra(Constants.EXTRA_TITLE,title)
        intent.putExtra(Constants.EXTRA_DESCRIPTION,description)
        intent.putExtra(Constants.EXTRA_PRIORITY,priority)
        intent.putExtra(Constants.EXTRA_ID,id)
        getResult.launch(intent)
    }

}