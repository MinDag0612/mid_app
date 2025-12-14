package com.example.core.auth;

import com.google.firebase.auth.FirebaseUser;

public interface AuthService {
    void createUser(String email, String password, AuthServiceCallback callback);

    void signIn(String email, String password, AuthServiceCallback callback);

    void signOut();

    FirebaseUser getCurrentUser();

    interface AuthServiceCallback {
        void onComplete(boolean success, Exception exception);
    }
}
