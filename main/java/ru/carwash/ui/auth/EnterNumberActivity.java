package ru.carwash.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carwash.carwash.R;

import ru.carwash.ui.main.MainActivity;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class EnterNumberActivity extends AppCompatActivity {

    private EditText etRegion;
    private EditText etPhone;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_number);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {

            Log.d(MainActivity.TAG, "Phone: " + etPhone.getText().toString() + "\n" +
                    "Length: " + etPhone.getText().toString().trim().length());

            if(!isNumberValid(etPhone.getText().toString())) {
                Toast.makeText(EnterNumberActivity.this, "Неправильно набран номер", Toast.LENGTH_LONG).show();
                return;
            }

            Intent codeIntent = new Intent(EnterNumberActivity.this,SignupActivity.class);
            codeIntent.putExtra("phone",etPhone.getText());
            startActivity(codeIntent);
        });
        etPhone = findViewById(R.id.etPhone);
        etPhone.requestFocus();
        setMaskToEditText();
    }

    public boolean isNumberValid(String number) {
        //TODO("There is no info about phones yet")
        if(number.length() < 14) return false;
        return true;
    }
    private void setMaskToEditText() {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(etPhone);
        etPhone.setText(" "); // костылек, чтобы регион (+7) отображался сразу
    }
}