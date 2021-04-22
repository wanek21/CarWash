package ru.carwash.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/* Автомойка */
@Parcelize
data class CarWash(
        val name: String? = null,
        val address: String? = null
) : Parcelable