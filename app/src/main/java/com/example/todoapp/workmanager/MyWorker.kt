package com.example.todoapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.NotesApp

class MyWorker(val context: Context, val workerParameters: WorkerParameters) : Worker(context,workerParameters) {
    override fun doWork(): Result {
        val notesApp=applicationContext as NotesApp
        val notesDao= notesApp.getNotesdb().notesDao()
        notesDao.deleteNotesWorker(true)
        return Result.success()
    }

}