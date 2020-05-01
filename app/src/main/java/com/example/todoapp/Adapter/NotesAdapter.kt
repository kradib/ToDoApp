package com.example.todoapp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.ClickListeners.ItemClickListener
import com.example.todoapp.R
import com.example.todoapp.db.Note


class NotesAdapter(val listNotes:List<Note>, val itemClickListener: ItemClickListener): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return listNotes.size
    }

    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
       val note=listNotes[position]
        val title: String = note.title
        val description: String = note.description
        holder.titleView.text = title
        holder.descriptionView.text = description
        holder.checkBoxMarkStatus.isChecked=note.isTaskCompleted
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                itemClickListener.onClick(note)
            }
        })
        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{

            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                //Log.d("CheckedButton","It's clicked")
                note.isTaskCompleted=isChecked
               itemClickListener.onUpdate(note)
            }

        })

    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleView: TextView= itemView.findViewById(R.id.textViewTitle)
        val descriptionView:TextView = itemView.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus: CheckBox = itemView.findViewById(R.id.checkBoxCheckStatus)

    }

}