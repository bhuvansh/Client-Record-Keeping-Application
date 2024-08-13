package com.example.addressbook.roomDatabase

import androidx.room.TypeConverter
import com.example.addressbook.AddressDetails
import com.google.gson.Gson

class AddressDetailsConverter {

    @TypeConverter
    fun fromString(value: String): AddressDetails {
        return Gson().fromJson(value, AddressDetails::class.java)
    }

    @TypeConverter
    fun fromAddressDetails(addressDetails: AddressDetails): String {
        return Gson().toJson(addressDetails)
    }
}
