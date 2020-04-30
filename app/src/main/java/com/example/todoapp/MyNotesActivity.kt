package com.example.todoapp

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
import com.example.todoapp.AppConstant.FULL_NAME
import com.example.todoapp.AppConstant.USER_NAME
import com.example.todoapp.ClickListeners.ItemClickListener
import com.example.todoapp.Shared_pref_constant.SHARED_PREF
import com.example.todoapp.Shared_pref_constant.USERNAME
import com.example.todoapp.model.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MyNotesActivity: AppCompatActivity() {
    lateinit var UserName: String
    lateinit var FullName: String
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

        fabAdd.setOnClickListener( object: View.OnClickListener{
            override fun onClick(v: View?) {
               getDialogBoxOpened()
            }

        })

        val titlebar = "Hi, $UserName!. Welcome master."
        supportActionBar?.title = titlebar
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
                val note = Note(title.text.toString(), description.text.toString())
                if (!note.title.isEmpty() && !note.description.isEmpty()) {
                    noteList.add(note)
                    setRecyclerView()
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
                var intent= Intent(this@MyNotesActivity,DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE,note.title)
                intent.putExtra(AppConstant.DESCRIPTION,note.description)
                startActivity(intent)
            }

        }
        val notesAdapter = NotesAdapter(noteList,itemClickListener)
        val linearLayoutManager=LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation=RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager=linearLayoutManager
        recyclerViewNotes.adapter=notesAdapter
    }


}

