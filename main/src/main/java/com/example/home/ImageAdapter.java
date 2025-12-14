package com.example.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.core.note.NoteRepo;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<String> images;
    private final Context context;
    private final String noteId;
    NoteRepo noteRepo = new NoteRepo();

    public ImageAdapter(Context context, List<String> images, String noteId) {
        this.context = context;
        this.images = images;
        this.noteId = noteId;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String url = images.get(position);
        Glide.with(context).load(url).into(holder.imgItem);

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            images.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, images.size());

            // 🔥 nếu có backend (Firestore / API) thì xóa thêm ở đây
             noteRepo.removeImage(noteId, url);
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        ImageButton btnDelete;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
