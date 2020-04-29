package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSharedPreferences();
        checkLoginStatus();
    }

    private void setSharedPreferences() {
        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF,MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn=sharedPreferences.getBoolean(Shared_pref_constant.IS_LOGGED_IN,false);
        if(isLoggedIn){
            //take to note activity
            Intent intent= new Intent(SplashActivity.this, MyNotesActivity.class);
            startActivity(intent);
        }
        else{
            //take to login activity
            Intent intent= new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
