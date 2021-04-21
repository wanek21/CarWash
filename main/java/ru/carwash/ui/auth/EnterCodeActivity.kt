package ru.carwash.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.carwash.carwash.R
import com.fraggjkee.smsconfirmationview.SmsConfirmationView
import ru.carwash.controllers.DataProcessor

class EnterCodeActivity : AppCompatActivity() {

    private var etPhone: EditText? = null
    private var smsCodeView: SmsConfirmationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)
        smsCodeView = findViewById(R.id.sms_code_view)
        smsCodeView?.onChangeListener = SmsConfirmationView.OnChangeListener { code, isComplete ->
            if(isComplete && isCodeRight(code)) {
                DataProcessor.saveData(this,"isLogin",true)
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }

        etPhone = findViewById(R.id.etPhone)
        val phone = intent.extras!!["phone"].toString()
        etPhone?.setText(phone)
    }

    private fun isCodeRight(code: String): Boolean {
        return true
    }
}