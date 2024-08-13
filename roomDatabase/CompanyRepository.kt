package com.example.addressbook.roomDatabase

import com.example.addressbook.CompanyDetails
import kotlinx.coroutines.flow.Flow

class CompanyRepository(private val companyDao:CompanyDao){
    suspend fun addANewCompany(company:CompanyDetails){
        companyDao.addEntry(company)
    }
    
    suspend fun deleteACompany(company:CompanyDetails){
        companyDao.deleteEntry(company)
    }
    
    suspend fun updateACompany(company:CompanyDetails){
        companyDao.updateRow(company)
    }
    
    fun getAllCompanies(): Flow<List<CompanyDetails>> {
        return companyDao.getAllCompanies()
    }

    fun getACompany(companyName:String):Flow<CompanyDetails>{
        return companyDao.getACompany(companyName)
    }
}