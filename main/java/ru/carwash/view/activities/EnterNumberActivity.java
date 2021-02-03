package ru.carwash.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.carwash.carwash.R;

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
            Intent codeIntent = new Intent(EnterNumberActivity.this,EnterCodeActivity.class);
            codeIntent.putExtra("phone",etPhone.getText());
            startActivity(codeIntent);
        });
        etPhone = findViewById(R.id.etPhone);
        etPhone.requestFocus();
        setMaskToEditText();
    }

    private void setMaskToEditText() {
        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        FormatWatcher watcher = new MaskFormatWatcher(mask);
        watcher.installOn(etPhone);
        etPhone.setText(" "); // костылек, чтобы отображался регион (+7)
    }
}