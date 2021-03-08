package ru.carwash.carwash

import org.junit.Test
import org.junit.Assert.*
import ru.carwash.controllers.DataProcessor
import ru.carwash.controllers.RequestController
import ru.carwash.models.LoginUser
import ru.carwash.models.User

class RequestsTest {

    @Test
    fun requestsAreNotNull() {
        assertNotEquals(RequestController.instance.getApiService(),null)
    }

    @Test
    fun loginUser() {

        var response = RequestController.instance
                .getApiService()
                .login(LoginUser("test@gmai.com","pass"))
                .execute()
        if(response.errorBody() != null) System.out.println(response.errorBody()!!.source())
        System.out.println(response.message())
    }

    @Test
    fun registerIsUser() {
        var response = RequestController.instance
                .getApiService()
                .registerUser(User("testFirstName",
                    "testLastName",
                    "test@gmai.com",
                    "testPass",
                    "+7930430934",
                    "Niznyi Novgorod"))
                .execute().body()
        if(response == null) fail()
        assertEquals(response?.firstName, "testFirstName")
    }
}