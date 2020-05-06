package com.example.todoapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todoapp.R
import com.example.todoapp.model.Data

class BlogAdapter(val listData: List<Data>):RecyclerView.Adapter<BlogAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.blog_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog=listData[position]
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageView)
        holder.title.text=blog.title
        holder.descrition.text=blog.description
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView:ImageView=itemView.findViewById(R.id.blogView)
        val title: TextView=itemView.findViewById(R.id.blogTitle)
        val descrition: TextView=itemView.findViewById(R.id.blogDescription)
    }

}