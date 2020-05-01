package com.example.todoapp.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.utils.Shared_pref_constant

class SplashActivity: AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedPreferences()
        checkLoginStatus()
    }
    private fun setSharedPreferences() {

        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn=sharedPreferences.getBoolean(Shared_pref_constant.IS_LOGGED_IN,false)
        if (isLoggedIn){
            val intent= Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent=Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}