package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {

    String UserName;
    String FullName;
    FloatingActionButton fabAdd;
    TextView titleView;
    TextView descriptionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        fabAdd=findViewById(R.id.fabAddNotes);
        Intent intent=getIntent();
        FullName=intent.getStringExtra("FullName");
        UserName=intent.getStringExtra("UserName");
        String titlebar="Hi, "+UserName+"!. Welcome master.";
        getSupportActionBar().setTitle(titlebar);
        titleView=findViewById(R.id.Text_View_Title);
        descriptionView=findViewById(R.id.Text_View_Description);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogBoxOpened();

            }
        });


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
                titleView.setText(title.getText().toString());
                descriptionView.setText(description.getText().toString());
                dialog.hide();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

    }
}
