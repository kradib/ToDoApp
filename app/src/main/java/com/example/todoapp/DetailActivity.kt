package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity:AppCompatActivity() {
    lateinit var title: TextView
    lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindView()
        setIntentData()
    }

    private fun setIntentData() {

        val intent=intent
        title.text = intent.getStringExtra(AppConstant.TITLE)
        //Log.d("DetailActivity",intent.getStringExtra(AppConstant.TITLE))
        description.text = intent.getStringExtra(AppConstant.DESCRIPTION)
    }

    private fun bindView() {

        title=findViewById(R.id.TextViewTitle_Detail)
        description=findViewById(R.id.TextViewDescription_Detail)
    }
}