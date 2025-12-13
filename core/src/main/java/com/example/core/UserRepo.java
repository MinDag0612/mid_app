package com.example.core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepo {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Interface callback
    public interface Callback<T> {
        void onResult(T result);
    }


    public void saveUser(Context context, User user) {
        db.collection("User")
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    String userInfor = user.toString();
                    Toast.makeText(context, "Thêm thành công: " + userInfor, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
