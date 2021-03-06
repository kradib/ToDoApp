package com.example.todoapp.View

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
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
import com.example.todoapp.workmanager.MyWorker

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import java.util.concurrent.TimeUnit

class MyNotesActivity: AppCompatActivity() {
    var UserName: String=""
    var FullName: String=""
    val REQUEST_CODE_ADDNOTES=100
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
        fabButtonClick()
        setRecyclerView()
        setUpWorkManager()
    }

    private fun fabButtonClick() {
        fabAdd.setOnClickListener( object: View.OnClickListener{
            override fun onClick(v: View?) {
                //getDialogBoxOpened()
                val intent=Intent(this@MyNotesActivity,AddNotesActivity::class.java)
                startActivityForResult(intent,REQUEST_CODE_ADDNOTES)
            }

        })

        val titlebar = "Hi, $UserName!. Warm Welcome :)"
        supportActionBar?.title = titlebar
    }

    private fun setUpWorkManager() {
        val comstraints=Constraints.Builder().build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java,15,TimeUnit.MINUTES)
                .setConstraints(comstraints)
                .build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun getDataFromDatabase() {
        val notesApp=applicationContext as NotesApp
        val notesDao=notesApp.getNotesdb().notesDao()
        noteList.addAll(notesDao.getAll())
    }

//    private fun getDialogBoxOpened() {
//       val view= LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialogs_layout,null)
//        val title = view.findViewById<EditText>(R.id.Edit_Text_Title)
//        val description = view.findViewById<EditText>(R.id.Edit_Text_Description)
//        val buttonSubmit = view.findViewById<Button>(R.id.ToDoAddButton)
//        val buttonBack = view.findViewById<Button>(R.id.ToDoBackButton)
//        val dialog = AlertDialog.Builder(this@MyNotesActivity)
//                .setView(view).setCancelable(false).create()
//        dialog.show()
//        buttonSubmit.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                val note = Note(title=title.text.toString(), description = description.text.toString())
//                if (!note.title.isEmpty() && !note.description.isEmpty()) {
//                    noteList.add(note)
//                    addNoteToDb(note)
//
//                    dialog.hide()
//                } else {
//                    Toast.makeText(this@MyNotesActivity, "Can't skip both title & description :)", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//        buttonBack.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//               dialog.hide()
//            }
//        })
//
//
//    }

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
                intent.putExtra(AppConstant.IMAGE_PATH,note.imagePath)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null) {
            if (requestCode == REQUEST_CODE_ADDNOTES) {
                val title = data?.getStringExtra(AppConstant.TITLE)
                val description = data?.getStringExtra(AppConstant.DESCRIPTION)
                val imagepath = data?.getStringExtra(AppConstant.IMAGE_PATH)
                val notes = Note(title = title!!, description = description!!, imagePath = imagepath!!, isTaskCompleted = false)
                addNoteToDb(notes)
                noteList.add(notes)
                recyclerViewNotes.adapter?.notifyItemChanged(noteList.size - 1)

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator= menuInflater
        inflator.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId==R.id.menuitem){
            finish()
            //as of now we I logout button in menu
            //val intent: Intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            //startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


}

