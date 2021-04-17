package ru.carwash.data.remote

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.carwash.controllers.RequestController
import ru.carwash.controllers.ServerApi
import ru.carwash.dto.LoginRequest
import ru.carwash.dto.User
import ru.carwash.utils.Resource
import javax.inject.Inject

class WebService @Inject constructor(@ApplicationContext val context: Context) {

    private var serverApi: ServerApi = RequestController.instance.getApiService(context)

    fun register(user: User): Resource<String> {
        return Resource.success(null)
    }

    fun login(loginRequest: LoginRequest): Resource<String> {
        var resource: Resource<String> = Resource.error("Unknown error",null)

        // test
        resource = Resource.success("lsdkjf;asldkf")

        /*val loginResponse = serverApi
                .login(loginRequest)
                .execute()
        resource = if (loginResponse.body() != null) {
            val token = loginResponse.body()!!.access_token
            Log.d("test","token: $token")
            Resource.success(token)
        } else Resource.error("Internal server error",null)*/
        /*serverApi
                .login(loginRequest)
                .enqueue(object : Callback<LoginResponse?> {
                    override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                        resource = if (response.body() != null) {
                            val token = response.body()!!.access_token
                            Resource.success(token)
                        } else Resource.error("Internal server error",null)
                    }

                    override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                        Log.d("my", "Error login: $t")
                        resource = Resource.error("Internal server error",null)
                    }
                })*/
        return resource
    }
}