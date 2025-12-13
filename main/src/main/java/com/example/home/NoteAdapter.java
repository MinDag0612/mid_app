package com.example.home;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<Note> list;
    Context context;

    public NoteAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note n = list.get(position);
        holder.txtTitle.setText(n.title);
        holder.txtDate.setText(n.date);
        holder.txtContentPreview.setText(n.content);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDate, txtContentPreview;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContentPreview = itemView.findViewById(R.id.txtContentPreview);
        }
    }
}



