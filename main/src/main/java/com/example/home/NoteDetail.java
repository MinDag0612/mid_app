package com.example.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.core.note.NoteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteDetail extends AppCompatActivity {
    ImageButton btnBack, imageBtn;
    String title, content, noteId;
    EditText etTitle, etContent;
    NoteRepo noteRepo = new NoteRepo();
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable saveRunnable;
    static final long SAVE_DELAY = 600; // ms
    private static final int PICK_IMAGE = 1001;

    TextWatcher watcher;
    ImageButton shareBtn, deleteBtn;
    RecyclerView rvImages;
    ImageAdapter adapter;
    ProgressBar loadingUpload;

    private List<String> imageUrls = new ArrayList<>();
    private void scheduleSave() {
        if (noteId == null) {
            Log.d("AutoSave", "noteId is NULL → return");
            return;
        }

        if (saveRunnable != null) {
            handler.removeCallbacks(saveRunnable);
        }

        saveRunnable = () -> {
            String newTitle = etTitle.getText().toString().trim();
            String newContent = etContent.getText().toString().trim();

            noteRepo.updateNote(newTitle, newContent, noteId);
            Log.d("AutoSave", "Saved " + noteId + " " + newTitle + " " + newContent);
        };

        handler.postDelayed(saveRunnable, SAVE_DELAY);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_detail);

        init();
        setBtnBack();
        setEtContent();
        setEtTitle();
        setupAutoSave();
        setDeleteBtn(deleteBtn);
        setRvImages();
        setImageBtn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData(); // <-- ẢNH NẰM Ở ĐÂY
            loadingUpload.setVisibility(View.VISIBLE);

            MediaManager.get()
                    .upload(imageUri)
                    .callback(new UploadCallback() {
                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            loadingUpload.setVisibility(View.GONE);
                            String url = resultData.get("secure_url").toString();

                            noteRepo.addImage(noteId, url);

                            imageUrls.add(url);
                            adapter.notifyItemInserted(imageUrls.size() - 1);
                            Log.d("UPLOAD", "URL = " + url);
                        }

                        @Override public void onError(String requestId, ErrorInfo error) {
                            loadingUpload.setVisibility(View.GONE);
                        }
                        @Override public void onStart(String requestId) {}
                        @Override public void onProgress(String requestId, long bytes, long totalBytes) {}
                        @Override public void onReschedule(String requestId, ErrorInfo error) {}
                    })
                    .dispatch();

        }
    }


    public void init(){
        btnBack = findViewById(R.id.btnBack);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        deleteBtn = findViewById(R.id.deleteBtn);
        imageBtn = findViewById(R.id.imageBtn);
        rvImages = findViewById(R.id.rvImages);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        noteId = intent.getStringExtra("noteId");
        loadingUpload = findViewById(R.id.loadingUpload);


    }

    public void setBtnBack(){
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void setEtTitle(){
        etTitle.setText(title);
    }

    public void setEtContent() {
        etContent.setText(content);
    }

    private void setupAutoSave() {

        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scheduleSave();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etTitle.addTextChangedListener(watcher);
        etContent.addTextChangedListener(watcher);
    }

    public void setDeleteBtn(ImageButton deleteBtn) {
        deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa note này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Thực hiện xóa
                        noteRepo.deleteNote(noteId);
                        finish(); // đóng activity sau khi xóa
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        dialog.dismiss(); // hủy xóa
                    })
                    .show();
        });
    }

    public void setRvImages() {

        rvImages.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );

        noteRepo.getUrlByNoteId(noteId, new NoteRepo.OnImageUploaded() {
            @Override
            public void onSuccess(List<String> urls) {

                imageUrls.clear();
                imageUrls.addAll(urls);
                adapter =
                        new ImageAdapter(NoteDetail.this, imageUrls, noteId);
                rvImages.setAdapter(adapter);

                Log.d("NoteDetail",
                        "Loaded images: " + urls.size() + " noteId=" + noteId);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(
                        NoteDetail.this,
                        "Load ảnh thất bại",
                        Toast.LENGTH_SHORT
                ).show();
                Log.e("NoteDetail", "Load images error", e);
            }
        });
    }

    private void setImageBtn(){
        imageBtn.setOnClickListener(v -> {
            pickImage();
        });
    }

}