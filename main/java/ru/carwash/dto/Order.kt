package ru.carwash.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

/* Заказ */
@Parcelize
data class Order(
        var status: Int,
        var carWash: CarWash? = null,
        var car: Car? = null,
        var date // TODO
        : String? = null,
        var time // TODO
        : String? = null,
        var services: @RawValue ArrayList<Service>? = null, // список услуг в заказе
        var price: Double = 0.0
) : Parcelable {

        companion object {
                const val CANCELED_STATUS = 1
                const val COMPLETED_STATUS = 2
                const val ACCEPTED_STATUS = 3
        }
}