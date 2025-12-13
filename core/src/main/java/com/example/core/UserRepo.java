package com.example.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserRepo {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Interface callback
    public interface Callback<T> {
        void onResult(T result);
    }


    public void saveUser(Context context, User user, String userId) {
        db.collection("User")
                .document(userId)
                .set(user)
                .addOnSuccessListener(documentReference -> {
                    String userInfor = user.toString();
                    Toast.makeText(context, "Thêm thành công: " + userInfor, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void getUserById(String userId, Callback<User> callback) {
        db.collection("User")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null && doc.exists()) {
                            User user = doc.toObject(User.class);
                            callback.onResult(user); // trả về 1 user
                        } else {
                            callback.onResult(null); // không tìm thấy
                        }
                    } else {
                        Log.d("UserRepo", "Error getting document: ", task.getException());
                        callback.onResult(null);
                    }
                });
    }





}
