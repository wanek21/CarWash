package ru.carwash.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/* Класс является Parcelable для возможности передачи его через navigate*/
@Parcelize
data class Car(
        var id: Int,
        var brand: String? = null,
        var model: String? = null,
        var carNumber: String? = null,
        var region: String? = null,
        var category: String? = null ) : Parcelable