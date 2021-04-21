package ru.carwash.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.carwash.carwash.R
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.ui.main.MainActivity
import ru.carwash.utils.Status

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var toolbar: Toolbar? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toolbar = findViewById(R.id.toolbar)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPass)
        setActionBar(toolbar)
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin!!.setOnClickListener {
            viewModel.login(etEmail!!.text.toString(), etPassword!!.text.toString())
        }
        viewModel.loginStatus.observe(this, {
            if(it.status == Status.SUCCESS) {
                val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(mainActivity)
                finishAffinity()
            } else if(it.status == Status.ERROR) {
                Toast.makeText(this@LoginActivity,it.message,Toast.LENGTH_LONG).show()
            }
        })
    }



    private fun login(email: String, password: String) {
        /*val loginUser = LoginUser(email, password)
        RequestController.instance
                .getApiService(applicationContext)
                .login(loginUser)
                .enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                        if (response.body() != null) {
                            val token = response.body()!!.access_token
                            val sessionManager = SessionManager(applicationContext)
                            sessionManager.saveAuthToken(token) // сохраняем токен
                            sessionManager.login() // фиксируем, что юзер авторизовался и входить больше не нужно 
                            val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(mainActivity)
                            finishAffinity()
                        } else Toast.makeText(this@LoginActivity, "Internal server error", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        Log.d(MainActivity.TAG, "Error login: $t")
                        Toast.makeText(this@LoginActivity, "Internal server error", Toast.LENGTH_LONG).show()
                    }
                })*/
    }
}