package com.example.room_noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application){
    val allNotes: LiveData<MutableList<Note>>
    val repository:NoteRepository

    init {
        val dao=NoteDatabase.getInstance(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes=repository.allnotes
    }
     fun deleteNote(note:Note)=viewModelScope.launch {
         repository.delete(note)
     }
    fun updateNote(note:Note)=viewModelScope.launch {
        repository.update(note)
    }
    fun addNote(note:Note)=viewModelScope.launch {
        repository.insert(note)
    }
    fun deleteAllNotes()=viewModelScope.launch {
        repository.delteAllNotes()
    }
}