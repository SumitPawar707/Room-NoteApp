package com.example.room_noteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    var title:String,               //Column names
    var description:String,
    var priority:Int
){
    //Primary key(autoIncrement)
    @PrimaryKey(autoGenerate = true)
    var id=0
}
