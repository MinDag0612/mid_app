package com.midApp.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.home.Home;
import com.example.auth.Signup;




public class MainActivity extends AppCompatActivity {

    // Trong file MainActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // (Lưu ý về dòng này, xem Nguyên nhân 2)
        setContentView(R.layout.activity_main); // Giữ lại dòng

        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
        finish();
    }
}