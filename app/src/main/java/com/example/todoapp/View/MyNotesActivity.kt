package com.example.todoapp.View

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Adapter.NotesAdapter
import com.example.todoapp.utils.AppConstant
import com.example.todoapp.utils.AppConstant.FULL_NAME
import com.example.todoapp.utils.AppConstant.USER_NAME
import com.example.todoapp.ClickListeners.ItemClickListener
import com.example.todoapp.NotesApp
import com.example.todoapp.R
import com.example.todoapp.db.Note
import com.example.todoapp.utils.Shared_pref_constant.SHARED_PREF
import com.example.todoapp.utils.Shared_pref_constant.USERNAME

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MyNotesActivity: AppCompatActivity() {
    var UserName: String=""
    var FullName: String=""
    lateinit var fabAdd: FloatingActionButton
    lateinit var recyclerViewNotes: RecyclerView
    var noteList = ArrayList<Note>()
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        setUpSharedPreference()
        bindView()
        getIntentData()
        getDataFromDatabase()
        fabAdd.setOnClickListener( object: View.OnClickListener{
            override fun onClick(v: View?) {
               getDialogBoxOpened()
            }

        })

        val titlebar = "Hi, $UserName!. Welcome master."
        supportActionBar?.title = titlebar
        setRecyclerView()
    }

    private fun getDataFromDatabase() {
        val notesApp=applicationContext as NotesApp
        val notesDao=notesApp.getNotesdb().notesDao()
        noteList.addAll(notesDao.getAll())
    }

    private fun getDialogBoxOpened() {
       val view= LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialogs_layout,null)
        val title = view.findViewById<EditText>(R.id.Edit_Text_Title)
        val description = view.findViewById<EditText>(R.id.Edit_Text_Description)
        val buttonSubmit = view.findViewById<Button>(R.id.ToDoAddButton)
        val buttonBack = view.findViewById<Button>(R.id.ToDoBackButton)
        val dialog = AlertDialog.Builder(this@MyNotesActivity)
                .setView(view).setCancelable(false).create()
        dialog.show()
        buttonSubmit.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val note = Note(title=title.text.toString(), description = description.text.toString())
                if (!note.title.isEmpty() && !note.description.isEmpty()) {
                    noteList.add(note)
                    addNoteToDb(note)

                    dialog.hide()
                } else {
                    Toast.makeText(this@MyNotesActivity, "Can't skip both title & description :)", Toast.LENGTH_SHORT).show()
                }
            }
        })
        buttonBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
               dialog.hide()
            }
        })


    }

    private fun addNoteToDb(note: Note) {
        val notesApp=applicationContext as NotesApp
        val notesDao=notesApp.getNotesdb().notesDao()
        notesDao.insert(note)


    }

    private fun getIntentData() {
        val intent = intent
        if(intent.hasExtra(FULL_NAME)&& intent.hasExtra(USER_NAME)) {
            FullName = intent.getStringExtra(FULL_NAME)
            UserName = intent.getStringExtra(USER_NAME)
        }
        if (UserName.isEmpty()) {
            UserName = sharedPreferences.getString(USERNAME, "")!!
        }
    }

    private fun bindView() {
        fabAdd=findViewById(R.id.fabAddNotes);
        recyclerViewNotes=findViewById(R.id.recyclerViewNotes);
    }

    private fun setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private fun setRecyclerView() {
        var itemClickListener= object: ItemClickListener{
            override fun onClick(note: Note) {
                var intent= Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE,note.title)
                intent.putExtra(AppConstant.DESCRIPTION,note.description)
                startActivity(intent)
            }

            override fun onUpdate(note: Note) {
                //Toast.makeText(this@MyNotesActivity,"button clicked",Toast.LENGTH_SHORT).show()
                val notesApp=applicationContext as NotesApp
                val notesDao=notesApp.getNotesdb().notesDao()
                notesDao.updateNotes(note)
            }

        }
        val notesAdapter = NotesAdapter(noteList,itemClickListener)
        val linearLayoutManager=LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation=RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager=linearLayoutManager
        recyclerViewNotes.adapter=notesAdapter
    }


}

