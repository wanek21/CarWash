package ru.carwash.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.carwash.carwash.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(loginIntent);
        });
        btnSignup.setOnClickListener(v -> {
            Intent signupIntent = new Intent(WelcomeActivity.this,SignupActivity.class);
            startActivity(signupIntent);
        });
    }
}
