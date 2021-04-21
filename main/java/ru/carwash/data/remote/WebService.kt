package ru.carwash.data.remote

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.carwash.controllers.ServerApi
import ru.carwash.dto.*
import ru.carwash.utils.Resource
import javax.inject.Inject

class WebService @Inject constructor(
        @ApplicationContext val context: Context,
        private val serverApi: ServerApi) {

    //test
    private var carsList: ArrayList<Car> = ArrayList()

    // test
    init {
        carsList.add(Car(1,"Audi", "TT", "A999AA", "152", "Легковая"))
        carsList.add(Car(2,"Toyota", "Camry", "A234AA", "52", "Легковая"))
        carsList.add(Car(3,"ВАЗ", "2109", "р943од", "52", "Легковая"))
    }

    fun register(user: User): Resource<String> {
        return Resource.success(null)
    }

    fun login(loginRequest: LoginRequest): Resource<String> {
        var resource: Resource<String> = Resource.error("Unknown error", null)

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

    fun editCar(car: AddEditCar): Resource<String> {

        return Resource.success(null)
    }

    fun getCars(): Resource<ArrayList<Car>> {
        // test
        return Resource.success(carsList)
    }

    fun addCar(addCar: AddEditCar): Resource<String> {

        val car = Car(
                id = carsList.size,
                brand = addCar.brand,
                model = addCar.model,
                carNumber =  addCar.carNumber,
                region = addCar.region,
                category = addCar.category
        )
        carsList.add(car)

        Log.d("my",car.toString())
        return Resource.success(null)
    }
}