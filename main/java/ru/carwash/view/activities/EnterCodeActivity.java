package ru.carwash.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.carwash.carwash.R;

public class EnterCodeActivity extends AppCompatActivity {

    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        etPhone = findViewById(R.id.etPhone);
        String phone = getIntent().getExtras().get("phone").toString();
        etPhone.setText(phone);
    }
}