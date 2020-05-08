package com.example.todoapp.View

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.todoapp.utils.AppConstant
import com.example.todoapp.R

class DetailActivity:AppCompatActivity() {
    lateinit var title: TextView
    lateinit var description: TextView
    lateinit var imageset: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindView()
        setIntentData()
    }

    private fun setIntentData() {

        val intent=intent
        title.text = intent.getStringExtra(AppConstant.TITLE)
        description.text = intent.getStringExtra(AppConstant.DESCRIPTION)
        Glide.with(this@DetailActivity).load(intent.getStringExtra(AppConstant.IMAGE_PATH)).into(imageset)
    }

    private fun bindView() {

        title=findViewById(R.id.TextViewTitle_Detail)
        description=findViewById(R.id.TextViewDescription_Detail)
        imageset=findViewById(R.id.ImageDetail)
    }
}