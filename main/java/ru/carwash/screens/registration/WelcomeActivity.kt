package ru.carwash.screens.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.carwash.carwash.R

class WelcomeActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var btnSignup: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)
        btnLogin?.setOnClickListener(View.OnClickListener { v: View? ->
            val loginIntent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        })
        btnSignup?.setOnClickListener(View.OnClickListener { v: View? ->
            val signupIntent = Intent(this@WelcomeActivity, SignupActivity::class.java)
            startActivity(signupIntent)
        })
    }
}