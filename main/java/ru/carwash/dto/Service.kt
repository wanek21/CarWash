package ru.carwash.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/* Услуга автомойки */
@Parcelize
data class Service(var name: String, var price: Double) : Parcelable