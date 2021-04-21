package ru.carwash.dto

data class AddEditCar(
        var brand: String? = null,
        var model: String? = null,
        var carNumber: String? = null,
        var region: String? = null,
        var category: String? = null
)
