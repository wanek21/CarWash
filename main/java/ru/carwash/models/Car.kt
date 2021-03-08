package ru.carwash.models

import android.os.Parcel
import android.os.Parcelable

data class Car(
        var brand: String? = null,
        var model: String? = null,
        var carNumber: String? = null,
        var region: String? = null,
        var category: String? = null ) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(brand)
        parcel.writeString(model)
        parcel.writeString(carNumber)
        parcel.writeString(region)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}