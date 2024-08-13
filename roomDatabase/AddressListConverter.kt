package com.example.addressbook.roomDatabase

import androidx.room.TypeConverter
import com.example.addressbook.AddressDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddressListConverter {

    @TypeConverter
    fun fromString(value: String): List<AddressDetails> {
        val listType = object : TypeToken<List<AddressDetails>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<AddressDetails>): String {
        return Gson().toJson(list)
    }
}
