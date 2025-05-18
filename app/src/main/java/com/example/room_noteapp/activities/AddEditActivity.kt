package com.example.room_noteapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.room_noteapp.R
import com.example.room_noteapp.utils.Constants

class AddEditActivity : AppCompatActivity() {
    private lateinit var ediTextTitle:EditText
    private lateinit var editTextDescription:EditText
    private lateinit var numberPicer:NumberPicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit)



        //Initializing views
        ediTextTitle = findViewById(R.id.editText_title)
        editTextDescription = findViewById(R.id.editText_description)
        numberPicer = findViewById(R.id.NumberPicker)
        // Then use them
        numberPicer.maxValue = 10
        numberPicer.minValue = 0

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_closeicon)
        title="Add Note"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_menu_item ->{
                saveNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        var title=ediTextTitle.text.toString()
        var description=editTextDescription.text.toString()
        var priority=numberPicer.value

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this@AddEditActivity, "Please Insert text in title or description", Toast.LENGTH_SHORT).show()
            return
        }

        setResult(Constants.REQUEST_CODE,intent.apply {
            putExtra(Constants.EXTRA_TITLE,title)
            putExtra(Constants.EXTRA_DESCRIPTION,description)
            putExtra(Constants.EXTRA_PRIORITY,priority)
        })
        finish()

    }


}