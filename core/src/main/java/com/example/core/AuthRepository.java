package com.example.core;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private FirebaseAuth auth;

    public AuthRepository() {
        auth = FirebaseAuth.getInstance();
    }

    // Đăng ký user
    public void registerUser(String email, String password, final AuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if(task.isSuccessful()) {
                            callback.onComplete(true, null);
                        } else {
                            callback.onComplete(false, task.getException() != null ? task.getException().getMessage() : "Unknown error");
                        }
                    }
                });
    }

    // Đăng nhập user
    public void loginUser(String email, String password, final AuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if(task.isSuccessful()) {
                            callback.onComplete(true, null);
                        } else {
                            callback.onComplete(false, task.getException() != null ? task.getException().getMessage() : "Unknown error");
                        }
                    }
                });
    }

    // Lấy user hiện tại
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    // Logout
    public void logout() {
        auth.signOut();
    }

    // Interface callback
    public interface AuthCallback {
        void onComplete(boolean success, String errorMessage);
    }
}