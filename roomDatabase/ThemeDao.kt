package com.example.addressbook.roomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ThemeDao {
    @Query("SELECT * FROM theme_settings WHERE id = :id")
    suspend fun getColorSettings(id: Int = 0): Theme?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColorSettings(theme: Theme)

    @Update
    suspend fun updateColorSettings(theme: Theme)
}