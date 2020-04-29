package com.example.todoapp.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.ClickListeners.ItemClickListener;
import com.example.todoapp.MyNotesActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> listNotes;
    private ItemClickListener itemClickListener;
    public NotesAdapter(List<Note> list, ItemClickListener itemClickListener){
        this.listNotes=list;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        //bind data to viewholder
        final Note note=listNotes.get(position);
        String title=note.getTitle();
        String description=note.getDescription();
        //Log.d("mytitle", title);
        holder.titleView.setText(title);
        holder.descriptionView.setText(description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleView,descriptionView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.textViewTitle);
            descriptionView=itemView.findViewById(R.id.textViewDescription);
        }
    }
}
