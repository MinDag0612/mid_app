package com.example.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.core.auth.AuthRepository;
import com.example.core.user.User;
import com.example.core.user.UserRepo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    EditText edtFullname, edtEmailRegister, edtPassRegister, edtRePassRegister;
    Button btnRegister;
    AuthRepository authRepository = new AuthRepository();
    TextView txtGoLogin;
    UserRepo userRepo = new UserRepo();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        init();
        setBtnRegister();
        setTxtGoLogin();
    }

    private void init(){
        edtFullname = findViewById(R.id.edtFullname);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPassRegister = findViewById(R.id.edtPassRegister);
        edtRePassRegister = findViewById(R.id.edtRePassRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtGoLogin = findViewById(R.id.txtGoLogin);
    }

    private void setBtnRegister(){
        btnRegister.setOnClickListener(v -> {
            String fullname = edtFullname.getText().toString();
            String email = edtEmailRegister.getText().toString();
            String pass = edtPassRegister.getText().toString();
            String rePass = edtRePassRegister.getText().toString();
            registerUser(fullname, email, pass, rePass);
        });
    }

    private void registerUser(String fullname, String email, String pass, String rePass){
        if(fullname.isEmpty() || email.isEmpty() || pass.isEmpty() || rePass.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(rePass)){
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
        else{
            authRepository.registerUser(email, pass, (success, errorMessage) -> {
                if(success) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = firebaseUser.getUid();

                    userRepo.saveUser(this, new User(fullname, email), userId);


                    Intent intent = new Intent(this, Login.class);
                    intent.putExtra("email", email);
                    intent.putExtra("pass", pass);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setTxtGoLogin(){
        txtGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });
    }


}
