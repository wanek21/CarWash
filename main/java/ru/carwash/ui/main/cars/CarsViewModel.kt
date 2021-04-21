package ru.carwash.ui.main.cars

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.carwash.data.repositories.CarsRepository
import ru.carwash.data.repositories.UserRepository
import ru.carwash.dto.AddEditCar
import ru.carwash.dto.Car
import ru.carwash.utils.Resource
import ru.carwash.utils.SingleLiveEvent
import ru.carwash.utils.Status
import javax.inject.Inject

/* ViewModel для всех фрагментов вкладки с авто */
@HiltViewModel
class CarsViewModel @Inject constructor(private val carsRepository: CarsRepository) : ViewModel() {

    private val _cars: MutableLiveData<Resource<ArrayList<Car>>> = MutableLiveData()
    val cars: LiveData<Resource<ArrayList<Car>>> = _cars

    private val _addingCarStatus: MutableLiveData<Resource<String>> = MutableLiveData()
    val addingCarStatus: LiveData<Resource<String>> = _addingCarStatus

    private val _editingCarStatus: MutableLiveData<Resource<String>> = MutableLiveData()
    val editingCarStatus: LiveData<Resource<String>> = _editingCarStatus

    // для навигации
    private val _successAddingCar = SingleLiveEvent<Any>()
    val successAddingCar: LiveData<Any>
        get() = _successAddingCar

    // для навигации
    private val _successEditingCar = SingleLiveEvent<Any>()
    val successEditingCar: LiveData<Any>
        get() = _successEditingCar

    init {
        _cars.value = Resource.loading(null)
        Log.d("my","carsViewModel init")
    }

    fun addCar(car: AddEditCar) {
        var isCarValid = isCarInfoValid(car)
        viewModelScope.launch {
            _addingCarStatus.value = if(isCarValid.status == Status.SUCCESS) {
                carsRepository.addCar(car)
            } else isCarValid

            _addingCarStatus.also {
                if(it.value?.status == Status.SUCCESS) // если добавление авто прошло успешно
                    successAddingCar()
            }
        }
    }

    fun editCar(car: AddEditCar) {
        val isCarValid = isCarInfoValid(car)
        viewModelScope.launch {
            _editingCarStatus.value = if(isCarValid.status == Status.SUCCESS) {
                carsRepository.editCar(car)
            } else isCarValid

            _editingCarStatus.also {
                if(it.value?.status == Status.SUCCESS) // если изменение авто прошло успешно
                {
                    successEditingCar()
                    Log.d("my","success editing")
                } else Log.d("my","error editing: ${it.value?.message}")
            }
        }
    }

    private fun successAddingCar() {
        _successAddingCar.call()
    }
    private fun successEditingCar() {
        _successEditingCar.call()
    }
    fun getCars() {
        viewModelScope.launch {
            _cars.value = carsRepository.getCars()
        }
    }

    private fun isCarInfoValid(car: AddEditCar): Resource<String> {
        if(car.brand != null) {
            if(car.brand.equals(""))
                return Resource.error("Марка авто не должна быть пустой",null)
            else if(!car.brand!!.matches(Regex("[A-Za-z0-9а-яА-Я\\s]+")))
                return Resource.error("Марка авто может содержать только кириллицу, латинские буквы и цифры", null)
        } else return Resource.error("Марка авто не должна быть пустой",null)

        if(car.model != null) {
            if(car.model.equals(""))
                return Resource.error("Модель авто не должна быть пустой",null)
            else if(!car.model!!.matches(Regex("[A-Za-z0-9а-яА-Я.\\s]+")))
                return Resource.error("Модель авто может содержать только кириллицу, латинские буквы, цифры и точку", null)
        } else return Resource.error("Модель авто не должна быть пустой",null)

        if(car.carNumber != null) {
            if(car.carNumber.equals(""))
                return Resource.error("Номер авто не должн быть пустым",null)
            else if(!car.carNumber!!.matches(Regex("[A-Za-z0-9-_а-яА-Я\\s]+")))
                return Resource.error("Номер авто может содержать только кириллицу, латинские буквы, цифры, а также \"_\" и \"-\"", null)

            car.carNumber = car.carNumber!!.toUpperCase()
        } else return Resource.error("Номер авто не должен быть пустым",null)

        if(car.region != null) {
            if(!car.region!!.matches(Regex("^[a-zA-Z0-9_.-]*\$")))
                return Resource.error("Регион может содержать только кириллицу, латинские буквы и цифры",null)
        } else car.region = ""

        if(car.category != null) {
            if(!car.category!!.matches(Regex("[A-Za-zа-яА-Я\\s]+")))
                return Resource.error("Категория может содержать только кириллицу и латинские буквы",null)
        } else return Resource.error("Категория не может быть пустой",null)

        return Resource.success(null)
    }
}