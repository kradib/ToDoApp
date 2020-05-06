package com.example.todoapp.View

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.todoapp.Adapter.BlogAdapter
import com.example.todoapp.R
import com.example.todoapp.model.JsonResponse


class BlogActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindView()
        getBlogs()
    }
    private fun getBlogs(){
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(JsonResponse::class.java,object : ParsedRequestListener<JsonResponse>{
                    override fun onResponse(response: JsonResponse?) {
                        setRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                     Log.d("BlogActivity",anError?.localizedMessage)
                    }

                })

    }
    private fun bindView() {
        recyclerView = findViewById(R.id.recyclerViewBlogs)
    }
    private fun setRecyclerView(response: JsonResponse?) {
        val blogAdapter=BlogAdapter(response!!.data)
        val linearLayoutManager= LinearLayoutManager(this)
        linearLayoutManager.orientation=RecyclerView.VERTICAL
        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter=blogAdapter
    }
}
