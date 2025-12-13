package com.example.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.AuthRepository;
import com.example.core.User;
import com.example.core.UserRepo;
import com.example.home.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText edtEmailLogin, edtPassLogin;
    Button btnLogin;
    AuthRepository authRepository = new AuthRepository();
    UserRepo userRepo = new UserRepo();
    TextView txtGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String email = intent.hasExtra("email") ? intent.getStringExtra("email") : "";
        String pass = intent.hasExtra("pass") ? intent.getStringExtra("pass") : "";

        init();
        edtEmailLogin.setText(email);
        edtPassLogin.setText(pass);
        setBtnLogin();
        setTxtGoRegister();

    }


    private void init() {
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtGoRegister = findViewById(R.id.txtGoRegister);
    }

    private void setBtnLogin() {
        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString();
            String pass = edtPassLogin.getText().toString();

            authRepository.loginUser(email, pass, (success, errorMessage) -> {
                if (success) {
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    String userId = authRepository.userId();
                    userRepo.getUserById(userId, user -> {
                        if (user != null) {
                            String fullname = user.getFullname();
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.putExtra("email", email);
                            intent.putExtra("userId", userId);
                            intent.putExtra("fullname", fullname);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("Login", "User not found: " + userId);
                            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setTxtGoRegister() {
        txtGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });

    }
}