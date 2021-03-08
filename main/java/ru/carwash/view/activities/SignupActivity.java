package ru.carwash.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import com.carwash.carwash.R;

import retrofit2.Call;
import ru.carwash.controllers.RequestController;
import ru.carwash.models.User;

public class SignupActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar = findViewById(R.id.toolbar);
        btnRegister = findViewById(R.id.btnSignup);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finishAffinity();
        });
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        //final Call<User> userCall = RequestController.Companion.getInstance().getApiService().registerUser(new User());
    }
}