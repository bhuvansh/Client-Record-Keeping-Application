package com.example.addressbook.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_settings")
data class Theme(
    @PrimaryKey val id: Int = 0, // Assuming there's only one set of color settings
    val itemHeadingColor: Int,
    val itemSubHeadingBgColor: Int,
    val itemSubHeadingTextColor: Int,
    val itemValueTextColor: Int,
    val itemValueBgColor: Int,
    val itemScreenColor: Int
)