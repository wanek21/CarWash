package ru.carwash.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.dto.User
import ru.carwash.utils.Resource
import ru.carwash.utils.Status


@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var btnRegister: Button? = null
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etCity: EditText? = null
    private var etPhone: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    private val viewModel: SignUpActivityViewModel by viewModels()

    private val regStatusObserver = Observer<Resource<String>> {
        if (it.status == Status.SUCCESS) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else if (it.status == Status.ERROR) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        toolbar = findViewById(R.id.toolbar)
        etFirstName = findViewById(R.id.editTextTextPersonName)
        etLastName = findViewById(R.id.editTextTextPersonLastName)
        etCity = findViewById(R.id.etCity)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPass)
        btnRegister = findViewById(R.id.btnSignup)
        btnRegister?.setOnClickListener {
            val user = User(firstName = etFirstName?.text.toString(),
                    lastName = etLastName?.text.toString(),
                    city = etCity?.text.toString(),
                    phone = etPhone?.text.toString(),
                    email = etEmail?.text.toString(),
                    password = etPassword?.text.toString()
            )
            viewModel.register(user)
        }

        viewModel.regStatus.observe(this, regStatusObserver)

        setActionBar(toolbar)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
        //setMaskToEditText()
    }


}