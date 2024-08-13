package com.example.addressbook

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addressbook.roomDatabase.CompanyRepository
import com.example.addressbook.roomDatabase.Graph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(private val companyRepository:CompanyRepository = Graph.companyRepository): ViewModel(){

    private val _addressList = mutableStateOf<List<CompanyDetails>>(emptyList())
    val addressList:State<List<CompanyDetails>> get()= _addressList

    private val _partyTypeList = mutableStateOf<List<String>>(listOf("Wholesaler","retailer","Broker","Friend","wips","wholetype"))
    val partyTypeList: State<List<String>> get()=_partyTypeList

    private val _companyNameList = mutableStateOf<List<String>>(emptyList())
    val companyNameList:State<List<String>> get()=_companyNameList

    private val _cityList = mutableStateOf<List<String>>(emptyList())
    val cityList:State<List<String>> get()=_cityList

    private val _stateList = mutableStateOf<List<String>>(emptyList())
    val stateList:State<List<String>> get()=_stateList

//    lateinit var allCompanies: Flow<List<CompanyDetails>>
    init{
        viewModelScope.launch {
            companyRepository.getAllCompanies().collect{companies->
                _addressList.value = companies
            }
        }
    }

    fun addNewAddressItem(company: CompanyDetails){
        viewModelScope.launch(Dispatchers.IO){
            companyRepository.addANewCompany(company)
        }
//        _addressList.value = _addressList.value + company
        val partyType = company.partyType
        val companyName = company.companyName

        //for adding a new company name to the list
        if(!_companyNameList.value.contains(companyName))
        {
            _companyNameList.value = _companyNameList.value + companyName.trim()
            _companyNameList.value = _companyNameList.value.sorted()
        }
        //for adding a new
        if(!_partyTypeList.value.contains(partyType))
        {
            _partyTypeList.value = _partyTypeList.value + partyType.trim()
            _partyTypeList.value = _partyTypeList.value.sorted()
        }

        //for adding a new city to the list
        company.Addresses.forEach{address->
            val city = address.City
            if(!_cityList.value.contains(city))
            {
                _cityList.value = _cityList.value + city.trim()
                _cityList.value = _cityList.value.sorted()
            }
        }

        //for adding a new state to the list
        company.Addresses.forEach{address->
            val state = address.State
            if(!_stateList.value.contains(state))
            {
                _stateList.value = _stateList.value + state.trim()
                _stateList.value = _stateList.value.sorted()
            }
        }

    }

    fun filterPartyType(str:String):List<String>{

        var newStr = str.trim()
        newStr = newStr.lowercase()

        val temp= mutableListOf<String>()

        if(newStr.isEmpty())
            return _partyTypeList.value

        _partyTypeList.value.forEach{

            var newIt = it.lowercase()
            var i=0
            var j=0
            var flag=true
            while(i<newStr.length && j<newIt.length)
            {
                if(newStr[i]!=newIt[j])
                {
                    flag=false
                    break
                }
                i+=1
                j+=1
            }
            if(flag)
                temp.add(it)
        }
        return temp
    }

    fun filterCompanyName(str:String):List<String>{

        var newStr = str.trim()
        val temp= mutableListOf<String>()

        if(str.isEmpty())
            return _companyNameList.value

        _companyNameList.value.forEach{
            var newIt = it.lowercase()
            var i=0
            var j=0
            var flag=true
            while(i<newStr.length && j<newIt.length)
            {
                if(newStr[i]!=newIt[j])
                {
                    flag=false
                    break
                }
                i+=1
                j+=1
            }
            if(flag)
                temp.add(it)
        }
        return temp
    }

    fun filterCity(str:String):List<String>{

        var newStr = str.trim()
        val temp= mutableListOf<String>()

        if(str.isEmpty())
            return _cityList.value

        _cityList.value.forEach{
            var newIt = it.lowercase()
            var i=0
            var j=0
            var flag=true
            while(i<newStr.length && j<newIt.length)
            {
                if(newStr[i]!=newIt[j])
                {
                    flag=false
                    break
                }
                i+=1
                j+=1
            }
            if(flag)
                temp.add(it)
        }
        return temp
    }

    fun filterState(str:String):List<String>{

        var newStr = str.trim()
        val temp= mutableListOf<String>()

        if(str.isEmpty())
            return _stateList.value

        _stateList.value.forEach{
            var newIt = it.lowercase()
            var i=0
            var j=0
            var flag=true
            while(i<newStr.length && j<newIt.length)
            {
                if(newStr[i]!=newIt[j])
                {
                    flag=false
                    break
                }
                i+=1
                j+=1
            }
            if(flag)
                temp.add(it)
        }
        return temp
    }

    fun getFilteredResults(companyName: String, partyType: String, city: String, state: String): List<CompanyDetails> {
        var temp = _addressList.value

        // Apply all filters if they are not empty
        if (companyName.isNotEmpty()) {
            temp = temp.filter { it.companyName.equals(companyName, ignoreCase = true) }
        }

        if (partyType.isNotEmpty()) {
            temp = temp.filter { it.partyType.equals(partyType, ignoreCase = true) }
        }

        if (city.isNotEmpty()) {
            temp = temp.filter { companyDetail ->
                companyDetail.Addresses.any { address -> address.City.equals(city, ignoreCase = true) }
            }
        }

        if (state.isNotEmpty()) {
            temp = temp.filter { companyDetail ->
                companyDetail.Addresses.any { address -> address.State.equals(state, ignoreCase = true) }
            }
        }

        return temp.sortedBy { it.companyName }
    }

    fun deleteCompany(company:CompanyDetails){
        viewModelScope.launch(){
            companyRepository.deleteACompany(company)
        }
    }

    fun updateCompany(company:CompanyDetails){
        viewModelScope.launch {
            companyRepository.updateACompany(company)
        }
    }

    fun getACompany(companyName:String):CompanyDetails?{
        var response :CompanyDetails? = null
        viewModelScope.launch {
            response = companyRepository.getACompany(companyName).firstOrNull()
        }
        return response
    }

}