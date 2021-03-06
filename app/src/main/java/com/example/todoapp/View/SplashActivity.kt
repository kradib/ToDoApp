package com.example.todoapp.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R
import com.example.todoapp.onboarding.OnboardingActivity
import com.example.todoapp.utils.Shared_pref_constant
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity: AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        bindView()
        setSharedPreferences()
        checkLoginStatus()
        getFCMToken()
        loginButtonClicked()
    }

    private fun loginButtonClicked() {
        imageView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                checkLoginStatus()
            }

        })
    }

    private fun bindView() {
      imageView=findViewById(R.id.splashImage)
    }

    private fun getFCMToken() {
        val TAG="mysplashactivity"
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast

                    Log.d(TAG, token)
                    //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })
    }

    private fun setSharedPreferences() {

        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF, Context.MODE_PRIVATE)
    }

    private fun checkLoginStatus() {
        val isLoggedIn=sharedPreferences.getBoolean(Shared_pref_constant.IS_LOGGED_IN,false)
        val onborading_success= sharedPreferences.getBoolean(Shared_pref_constant.ON_BOARDED_SUCCESSFULLY,false)
        if (isLoggedIn){
            val intent= Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
        }
        else{
            //
            if(onborading_success){
                val intent=Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                startActivity(intent)
            }
        }
    }

}