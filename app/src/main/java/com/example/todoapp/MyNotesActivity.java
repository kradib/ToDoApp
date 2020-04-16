package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyNotesActivity extends AppCompatActivity {

    String UserName;
    String FullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        Intent intent=getIntent();
        FullName=intent.getStringExtra("FullName");
        UserName=intent.getStringExtra("UserName");
            String titlebar="Hi, "+UserName+"!. Welcome master.";
            getSupportActionBar().setTitle(titlebar);

    }
}
