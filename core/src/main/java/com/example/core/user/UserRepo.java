package com.example.core.user;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepo {
    private final FirebaseFirestore db;
    private final UserSource userSource;

    // Interface callback
    public interface Callback<T> {
        void onResult(T result);
    }

    public interface UserSource {
        void getUserById(String userId, Callback<User> callback);
    }

    public UserRepo() {
        this(FirebaseFirestore.getInstance());
    }

    public UserRepo(FirebaseFirestore db) {
        this(db, new FirestoreUserSource(db));
    }

    public UserRepo(UserSource userSource) {
        this(null, userSource);
    }

    UserRepo(FirebaseFirestore db, UserSource userSource) {
        this.db = db;
        this.userSource = userSource;
    }

    public void saveUser(Context context, User user, String userId) {
        if (db == null) {
            throw new IllegalStateException("Firestore is not initialized");
        }
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
        userSource.getUserById(userId, callback);
    }

    private static class FirestoreUserSource implements UserSource {
        private final FirebaseFirestore firestore;

        FirestoreUserSource(FirebaseFirestore firestore) {
            this.firestore = firestore;
        }

        @Override
        public void getUserById(String userId, Callback<User> callback) {
            firestore.collection("User")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc != null && doc.exists()) {
                                    User user = doc.toObject(User.class);
                                    callback.onResult(user);
                                } else {
                                    callback.onResult(null);
                                }
                            } else {
                                Log.d("UserRepo", "Error getting document: ", task.getException());
                                callback.onResult(null);
                            }
                        }
                    });
        }
    }
}
