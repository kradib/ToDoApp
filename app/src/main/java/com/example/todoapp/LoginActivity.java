package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText full_name,user_name;
    Button login_button;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        full_name=findViewById(R.id.full_name);
        user_name=findViewById(R.id.user_name);
        login_button=findViewById(R.id.login_button);
        setUpSharedPreferences();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("LoginActivity","button clicked");
                String FullName=full_name.getText().toString();
                String UserName=user_name.getText().toString();

                if(!FullName.isEmpty() && !UserName.isEmpty()){
                    Intent intent =new Intent(LoginActivity.this,MyNotesActivity.class);
                    intent.putExtra(AppConstant.FULL_NAME,FullName);
                    intent.putExtra(AppConstant.USER_NAME,UserName);
                    startActivity(intent);
                    //Log in status
                    saveLoginStatus();
                    saveUserName(UserName);

                }
                else{
                    Toast.makeText(LoginActivity.this, "You need to put your name buddy, don't forget your user name too", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void saveUserName(String userName) {
        editor=sharedPreferences.edit();
        editor.putString(Shared_pref_constant.USER_NAME,userName);
        editor.apply();
    }

    private void saveLoginStatus() {
        editor=sharedPreferences.edit();
        editor.putBoolean(Shared_pref_constant.IS_LOGGED_IN,true);
        editor.apply();
    }

    private void setUpSharedPreferences() {
        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF,MODE_PRIVATE);
    }
}
