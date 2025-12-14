package com.example.core.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthService implements AuthService {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthService() {
        this(FirebaseAuth.getInstance());
    }

    FirebaseAuthService(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void createUser(String email, String password, AuthServiceCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    callback.onComplete(task.isSuccessful(), task.getException());
                });
    }

    @Override
    public void signIn(String email, String password, AuthServiceCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    callback.onComplete(task.isSuccessful(), task.getException());
                });
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
}
