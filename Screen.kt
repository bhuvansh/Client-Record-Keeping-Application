package com.example.addressbook

sealed class Screen(val route:String){

    object HomeScreen:Screen("homescreen")
    object AddScreen:Screen("addscreen")
    object SearchScreen:Screen("searchscreen")
    object ResultScreen:Screen("resultscreen")
    object ItemScreen:Screen("itemscreen")
    object EditScreen:Screen("editscreen")
    object ColorScreen:Screen("colorscreen")
}