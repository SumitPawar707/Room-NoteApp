package com.example.room_noteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class),version=1,exportSchema=false)
abstract class NoteDatabase:RoomDatabase(){
    abstract fun getNoteDao():NoteDao

    //companion object is used for to create only one instance of the database. avoid creating multiple instances
    companion object{
        private var INSTANCE:NoteDatabase?=null

        fun getInstance(context: Context):NoteDatabase{
            //If database is not null then return it
            //If it is null then create it and return it
            return INSTANCE ?: synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}