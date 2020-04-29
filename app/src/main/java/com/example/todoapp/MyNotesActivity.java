package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.Adapter.NotesAdapter;
import com.example.todoapp.ClickListeners.ItemClickListener;
import com.example.todoapp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    String UserName;
    String FullName;
    FloatingActionButton fabAdd;
    RecyclerView recyclerViewNotes;
    ArrayList<Note> noteList =new ArrayList<>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        setUpSharedPreference();
        bindView();
        getIntentData();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogBoxOpened();

            }
        });
        String titlebar="Hi, "+UserName+"!. Welcome master.";
        getSupportActionBar().setTitle(titlebar);


    }

    private void setUpSharedPreference() {
        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF,MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent=getIntent();
        FullName=intent.getStringExtra(AppConstant.FULL_NAME);
        UserName=intent.getStringExtra(AppConstant.USER_NAME);
        if(TextUtils.isEmpty(UserName)){
            UserName=sharedPreferences.getString(Shared_pref_constant.USER_NAME,"");
        }
    }

    private void bindView() {
        fabAdd=findViewById(R.id.fabAddNotes);
        recyclerViewNotes=findViewById(R.id.recyclerViewNotes);
    }

    private  void getDialogBoxOpened(){
        View view= LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialogs_layout,null);
        final EditText title=view.findViewById(R.id.Edit_Text_Title);
        final EditText description=view.findViewById(R.id.Edit_Text_Description);
        Button buttonSubmit=view.findViewById(R.id.ToDoAddButton);
        Button buttonBack=view.findViewById(R.id.ToDoBackButton);
        final AlertDialog dialog= new AlertDialog.Builder(MyNotesActivity.this)
                .setView(view).setCancelable(false).create();

        dialog.show();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=new Note();
                note.setTitle(title.getText().toString());
                note.setDescription(description.getText().toString());
                if(!TextUtils.isEmpty(note.getTitle()) && !TextUtils.isEmpty(note.getDescription())){
                    noteList.add(note);
                    setRecyclerView();
                    dialog.hide();
                }
                else if(TextUtils.isEmpty(note.getDescription()) && !TextUtils.isEmpty(note.getTitle())){
                    Toast.makeText(MyNotesActivity.this, "Don't want to put description? Okay, we did it for you", Toast.LENGTH_SHORT).show();
                    note.setDescription(note.getTitle());
                    noteList.add(note);
                    setRecyclerView();
                    dialog.hide();
                }
                else{
                    Toast.makeText(MyNotesActivity.this, "Can't skip both title & description :)", Toast.LENGTH_SHORT).show();
                }

                //Log.d("note description", note.getDescription());


            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }

    private void setRecyclerView() {
        ItemClickListener itemClickListener=new ItemClickListener() {
            @Override
            public void onClick(Note note) {
                Log.d("Onclick", "onClick: Worked");
                Intent intent=new Intent(MyNotesActivity.this,DetailActivity.class);
                intent.putExtra(AppConstant.TITLE,note.getTitle());
                intent.putExtra(AppConstant.DESCRIPTION,note.getDescription());
                startActivity(intent);
            }
        };
        NotesAdapter notesAdapter=new NotesAdapter(noteList,itemClickListener);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(MyNotesActivity.this);
        //assign orientation
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}
