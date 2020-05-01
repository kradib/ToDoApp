package com.example.todoapp.ClickListeners

import com.example.todoapp.db.Note


interface ItemClickListener {
    fun onClick(note: Note)
    fun onUpdate(note: Note)
}