package com.example.appcore2

import android.os.Parcel
import android.os.Parcelable

data class DataModel(
    val name: String,
    val description: String,
    val price: Int,
    val image: Int,
    val brand: String,
    val year: String,
    val hp: String,
    val rating: Double,
    var borrow: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(price)
        parcel.writeInt(image)
        parcel.writeString(brand)
        parcel.writeString(year)
        parcel.writeString(hp)
        parcel.writeDouble(rating)
        parcel.writeString(borrow)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<DataModel> {
        override fun createFromParcel(parcel: Parcel): DataModel {
            return DataModel(parcel)
        }
        override fun newArray(size: Int): Array<DataModel?> {
            return arrayOfNulls(size)
        }
    }
}