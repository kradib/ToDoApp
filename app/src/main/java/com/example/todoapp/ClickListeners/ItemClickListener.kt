package com.example.todoapp.ClickListeners

import com.example.todoapp.model.Note

interface ItemClickListener {
    fun onClick(note:Note)
}