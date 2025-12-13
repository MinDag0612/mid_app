package com.example.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.AuthRepository;

public class Login extends AppCompatActivity {
    EditText edtEmailLogin, edtPassLogin;
    Button btnLogin;
    AuthRepository authRepository = new AuthRepository();
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

    private void init(){
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPassLogin = findViewById(R.id.edtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        txtGoRegister = findViewById(R.id.txtGoRegister);
    }

    private void setBtnLogin(){
        btnLogin.setOnClickListener(v -> {
            String email = edtEmailLogin.getText().toString();
            String pass = edtPassLogin.getText().toString();

            authRepository.loginUser(email, pass, (success, errorMessage) -> {
                if (success) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // Điều hướng
                } else {
                    Toast.makeText(this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setTxtGoRegister(){
        txtGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });

    }
}