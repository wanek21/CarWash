package ru.carwash.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/* Заказ */
data class Order(var status: Int) : Parcelable {
    var carWash: CarWash? = null
    var car: Car? = null
    var data // TODO
            : String? = null
    var time // TODO
            : String? = null
    var services // список услуг в заказе
            : ArrayList<Service>? = null
    var price = 0.0

    constructor(parcel: Parcel) : this(parcel.readInt()) {
        car = parcel.readParcelable(Car::class.java.classLoader)
        data = parcel.readString()
        time = parcel.readString()
        price = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeParcelable(car, flags)
        parcel.writeString(data)
        parcel.writeString(time)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }

        @JvmField
        var CANCELED_STATUS = 0
        @JvmField
        var ACCEPTED_STATUS = 1
        @JvmField
        var COMPLETED_STATUS = 2
    }
}