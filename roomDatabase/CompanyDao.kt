package com.example.addressbook.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.addressbook.CompanyDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CompanyDao(){

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addEntry(companyDetails:CompanyDetails)

    @Delete
    abstract suspend fun deleteEntry(companyDetails:CompanyDetails)

    @Update
    abstract suspend fun updateRow(companyDetails: CompanyDetails)

    @Query("Select * from `company-table`")
    abstract fun getAllCompanies(): Flow<List<CompanyDetails>>

    @Query("Select * from `company-table` where companyName=:companyName")
    abstract fun getACompany(companyName:String):Flow<CompanyDetails>
}
