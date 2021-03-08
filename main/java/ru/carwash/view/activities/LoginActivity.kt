package ru.carwash.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.carwash.carwash.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.carwash.controllers.DataProcessor.Companion.saveData
import ru.carwash.controllers.RequestController
import ru.carwash.controllers.RequestController.Companion.instance
import ru.carwash.controllers.SessionManager
import ru.carwash.models.LoginResponse
import ru.carwash.models.LoginUser
import ru.carwash.view.activities.MainActivity

class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var toolbar: Toolbar? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null

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
            if(isValidateEmailAndPass())
                login(etEmail!!.text.toString(), etPassword!!.text.toString())
        }
    }

    private fun isValidateEmailAndPass(): Boolean {
        val isEmailValid = etEmail?.validEmail {
            Toast.makeText(this,"Некорректный email",Toast.LENGTH_SHORT).show()
        }
        val isPasswordValid = etPassword?.validator()
                ?.nonEmpty()
                ?.minLength(6)
                ?.addErrorCallback {
                    Toast.makeText(this,"Пароль должен быть не меньше 6 символов",Toast.LENGTH_SHORT).show()
                }
                ?.check()

        return isEmailValid!! && isPasswordValid!!
    }

    private fun login(email: String, password: String) {
        val loginUser = LoginUser(email, password)
        RequestController.instance
                .getApiService(applicationContext)
                .login(loginUser)
                .enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                        if (response.body() != null) {
                            val token = response.body()!!.access_token
                            val sessionManager = SessionManager(applicationContext)
                            sessionManager.saveAuthToken(token)
                            saveData(this@LoginActivity, "isLogin", true)
                            val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(mainActivity)
                            finishAffinity()
                        } else Toast.makeText(this@LoginActivity, "Internal server error", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        Log.d(MainActivity.TAG, "Error login: $t")
                        Toast.makeText(this@LoginActivity, "Internal server error", Toast.LENGTH_LONG).show()
                    }
                })
    }
}