package com.example.todoapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.example.todoapp.db.NotesDatabase

class NotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext);

    }
    fun getNotesdb():NotesDatabase{
        return NotesDatabase.getInstance(this)
    }


}