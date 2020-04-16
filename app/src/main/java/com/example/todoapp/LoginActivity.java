package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText full_name,user_name;
    Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        full_name=findViewById(R.id.full_name);
        user_name=findViewById(R.id.user_name);
        login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("LoginActivity","button clicked");
                String FullName=full_name.getText().toString();
                String UserName=user_name.getText().toString();
                Intent intent =new Intent(LoginActivity.this,MyNotesActivity.class);
                intent.putExtra("FullName",FullName);
                intent.putExtra("UserName",UserName);
                if(!FullName.isEmpty() && !UserName.isEmpty()){
                    startActivity(intent);

                }
                else{
                    Toast.makeText(LoginActivity.this, "You need to put your name buddy, don't forget your user name too", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
