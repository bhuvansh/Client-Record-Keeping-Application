package com.example.addressbook.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.addressbook.CompanyDetails


@Database(entities = [CompanyDetails::class,Theme::class], version=2)
@TypeConverters(StringListConverter::class, AddressListConverter::class)
abstract class CompanyDatabase:RoomDatabase() {

    abstract fun companyDao():CompanyDao
    abstract fun themeDao():ThemeDao
}