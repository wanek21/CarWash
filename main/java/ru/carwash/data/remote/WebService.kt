package ru.carwash.data.remote

import android.content.Context
import android.util.Log
import android.widget.SearchView
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
    private var ordersList: ArrayList<Order> = ArrayList()
    private var servicesList: ArrayList<Service> = ArrayList()

    // test
    init {
        carsList.add(Car(1, "Audi", "TT", "A999AA", "152", "Легковая"))
        carsList.add(Car(2, "Toyota", "Camry", "A234AA", "52", "Легковая"))
        carsList.add(Car(3, "ВАЗ", "2109", "р943од", "52", "Легковая"))

        servicesList.add(Service(
                name = "Сполоснуть водой",
                price = 250
        ))
        servicesList.add(Service(
                name = "Техническая мойка",
                price = 150
        ))
        servicesList.add(Service(
                name = "Комплексная мойка",
                price = 350
        ))
        servicesList.add(Service(
                name = "Пылесос",
                price = 50
        ))
        servicesList.add(Service(
                name = "Протереть стекла",
                price = 100
        ))
        servicesList.add(Service(
                name = "Наружная мойка с коврами",
                price = 100
        ))
        val order1 = Order(
                status = Order.CANCELED_STATUS,
                carWash = CarWash(name = "Автомойка \"МИР\"", address = "Лейтенанта Шмидта, 1"),
                car = carsList[0],
                date = "22.04.2021",
                time = "13:50",
                services = servicesList,
                price = 450.0
                )
        val order2 = Order(
                status = Order.COMPLETED_STATUS,
                carWash = CarWash(name = "Автомойка \"МИР\"", address = "Лейтенанта Шмидта, 1"),
                car = carsList[0],
                date = "23.04.2021",
                time = "16:30",
                services = servicesList,
                price = 250.0
        )
        val order3 = Order(
                status = Order.ACCEPTED_STATUS,
                carWash = CarWash(name = "Автомойка \"МИР\"", address = "Лейтенанта Шмидта, 1"),
                car = carsList[0],
                date = "23.04.2021",
                time = "19:10",
                services = servicesList,
                price = 780.0
        )
        ordersList.add(order1)
        ordersList.add(order2)
        ordersList.add(order3)
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
                carNumber = addCar.carNumber,
                region = addCar.region,
                category = addCar.category
        )
        carsList.add(car)

        Log.d("my", car.toString())
        return Resource.success(null)
    }

    fun getOrders(): Resource<ArrayList<Order>> {
        return Resource.success(ordersList)
    }

    fun createOrder(order: Order): Resource<String> {
        return Resource.success(null)
    }

    fun sendReview(review: Review): Resource<String> {
        return Resource.success(null)
    }

    fun getAvailableServices(): Resource<ArrayList<Service>> {
        return Resource.success(servicesList)
    }
}