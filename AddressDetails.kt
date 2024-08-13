package com.example.addressbook

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressDetails(
    var Address:String,
    var City:String,
    var Pincode:String,
    var State:String
):Parcelable
