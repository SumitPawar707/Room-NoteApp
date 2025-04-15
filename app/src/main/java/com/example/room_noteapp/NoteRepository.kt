package com.example.room_noteapp

import androidx.lifecycle.LiveData

class NoteRepository (private var notesDao:NoteDao){
    val allnotes:LiveData<MutableList<Note>> = notesDao.getAllNotes()

    suspend fun insert(note:Note){
        notesDao.insert(note)
    }
    suspend fun update(note: Note){
        notesDao.update(note)
    }
    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

}
/*
🔹 Main Reasons for Using a Repository
Abstraction:
    The UI (ViewModel or Activity) doesn’t care whether data comes from Room, network, or a file.
    It just calls repository.getAllNotes().
Easier Testing:
    You can easily write test cases for your ViewModel by mocking the repository.
    If you directly use the DAO, testing becomes harder.
Separation of Concerns:
    ViewModel handles UI logic, Repository handles data logic. DAO handles SQL logic.

 🔹 Right Now, It May Look Like Repetition
Yes, your NoteRepository has similar methods like your NoteDao, but it's meant to grow.

Imagine later you want to:

Fetch from the internet if local data is empty
Cache online data into Room
Combine Firebase + Room
Log user actions while inserting/deleting notes

Then your Repository will have logic like:

kotlin
fun insert(note: Note) {
    log("Inserting note")
    if (isConnectedToInternet()) {
        firebaseService.insertNote(note)
    }
    notesDao.insert(note)
}
Now, the ViewModel still calls:

viewModelScope.launch {
    noteRepository.insert(note)
}
And it doesn’t care what happens behind the scenes.


 */