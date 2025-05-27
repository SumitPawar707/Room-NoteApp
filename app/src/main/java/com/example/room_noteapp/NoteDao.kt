package com.example.room_noteapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note:Note)

    @Update
    suspend fun  update(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    fun getAllNotes():LiveData<MutableList<Note>>
}
/*
🔹 1. What is Room?
Room is a part of Android Jetpack and it's an abstraction layer over SQLite. It helps you store and
manage persistent data (like notes) easily without having to write a lot of raw SQL.
Room has 3 major components:
1. Entity: The data class that defines the structure of your table (e.g., Note.kt).
2. DAO (Data Access Object): The interface that provides methods to interact with the database.
3. Database: The abstract class that holds the database and serves as the main access point.

🔹 2. Role of NoteDao (DAO)
NoteDao is an interface that contains methods to access your database. You don’t write full database logic here.
 You just declare the operations (insert, update, delete, query),
 and Room automatically generates the required code behind the scenes.

 🔹 3. Role of NoteDatabase
NoteDatabase is an abstract class that extends RoomDatabase. It tells Room:
> Which Entity (tables) to use: Note::class
> Which DAO to use: NoteDao
> It also provides a singleton instance of the database. Why singleton?
    To avoid memory leaks and resource misuse, we should only create one instance of the database throughout the app.
 */