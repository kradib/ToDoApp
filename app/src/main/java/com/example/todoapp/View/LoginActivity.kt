package com.example.todoapp.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.utils.AppConstant.FULL_NAME
import com.example.todoapp.utils.AppConstant.USER_NAME
import com.example.todoapp.R
import com.example.todoapp.utils.Shared_pref_constant
import com.example.todoapp.utils.Shared_pref_constant.IS_LOGGED_IN
import com.example.todoapp.utils.Shared_pref_constant.SHARED_PREF

class LoginActivity: AppCompatActivity() {
    lateinit var full_name: EditText
    lateinit var user_name: EditText
    lateinit var login_button: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindView()
        setUpSharedPreferences()

    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    private fun bindView() {
        full_name = findViewById(R.id.full_name)
        user_name = findViewById(R.id.user_name)
        login_button = findViewById(R.id.login_button)
        login_button.setOnClickListener {
            //Log.d("LoginActivity","button clicked");
            val FullName = full_name.text.toString()
            val UserName = user_name.text.toString()
            if (!FullName.isEmpty() && !UserName.isEmpty()) {
                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(FULL_NAME, FullName)
                intent.putExtra(USER_NAME, UserName)
                startActivity(intent)
                //Log in status
                saveLoginStatus()
                saveUserName(UserName)
            } else {
                Toast.makeText(this@LoginActivity, "You need to put your name buddy, don't forget your user name too", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserName(userName: String) {
        editor = sharedPreferences.edit()
        editor.putString(Shared_pref_constant.USERNAME, userName)
        editor.apply()
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }
}