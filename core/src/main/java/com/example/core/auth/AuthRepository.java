package com.example.core.auth;

import com.google.firebase.auth.FirebaseUser;

public class AuthRepository {
    private final AuthService authService;

    public AuthRepository() {
        this(new FirebaseAuthService());
    }

    public AuthRepository(AuthService authService) {
        this.authService = authService;
    }

    // Đăng ký user
    public void registerUser(String email, String password, final AuthCallback callback) {
        authService.createUser(email, password, (success, exception) -> {
            if(success) {
                callback.onComplete(true, null);
            } else {
                callback.onComplete(false, exception != null ? exception.getMessage() : "Unknown error");
            }
        });
    }

    // Đăng nhập user
    public void loginUser(String email, String password, final AuthCallback callback) {
        authService.signIn(email, password, (success, exception) -> {
            if(success) {
                callback.onComplete(true, null);
            } else {
                callback.onComplete(false, exception != null ? exception.getMessage() : "Unknown error");
            }
        });
    }



    // Logout
    public void logout() {
        authService.signOut();
    }

    // Interface callback
    public interface AuthCallback {
        void onComplete(boolean success, String errorMessage);
    }

    public String userId() {
        FirebaseUser user = authService.getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

}
