package com.example.home;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    Context context;
    OnNoteClickListener listener;
    List<Note> list;
    List<Note> filteredList;
    String highlightedNoteId = null;

    public NoteAdapter(Context context, List<Note> list, OnNoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.filteredList = new ArrayList<>(list);
    }

    public void setHighlightedById(String noteId) {
        highlightedNoteId = noteId;
        notifyDataSetChanged(); // refresh tất cả item, kiểm tra id để highlight
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
//        Note n = list.get(position);
        Note n = filteredList.get(position);

        holder.txtTitle.setText(n.title);
        holder.txtContentPreview.setText(n.content);

        if (n.date != null) {
            holder.txtDate.setText(n.date.toDate().toString());
        }
        else {
            holder.txtDate.setText("");
        }

        if (highlightedNoteId != null && highlightedNoteId.equals(n.id)) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF59D"));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    public int getFilteredPositionById(String noteId) {
        for (int i = 0; i < filteredList.size(); i++) {
            if (filteredList.get(i).id.equals(noteId)) return i;
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDate, txtContentPreview;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContentPreview = itemView.findViewById(R.id.txtContentPreview);

            itemView.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onNoteClick(filteredList.get(pos));
                }
            });

        }
    }

    public void filter(String keyword) {
        filteredList.clear();

        if (keyword == null || keyword.trim().isEmpty()) {
            filteredList.addAll(list);
        } else {
            keyword = keyword.toLowerCase();
            for (Note n : list) {
                if ((n.title != null && n.title.toLowerCase().contains(keyword)) ||
                        (n.content != null && n.content.toLowerCase().contains(keyword))) {
                    filteredList.add(n);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setData(List<Note> newList) {
        list.clear();
        list.addAll(newList);

        filteredList.clear();
        filteredList.addAll(newList);

        notifyDataSetChanged();
    }


}



