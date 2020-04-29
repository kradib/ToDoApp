package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyNotesActivity extends AppCompatActivity {

    String UserName;
    String FullName;
    FloatingActionButton fabAdd;
    TextView titleView;
    TextView descriptionView;
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
        titleView=findViewById(R.id.Text_View_Title);
        descriptionView=findViewById(R.id.Text_View_Description);
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
