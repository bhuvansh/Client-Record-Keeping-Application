package com.example.addressbook.roomDatabase

import android.app.Application

class AddressBook:Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(context=this)
    }
}