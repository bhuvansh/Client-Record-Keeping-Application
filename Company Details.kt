package com.example.addressbook

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "company-table")
@Parcelize
data class CompanyDetails(
    @PrimaryKey
    val companyName:String,
    @ColumnInfo(name="owner-name")
    val ownerName:List<String>,
    @ColumnInfo(name="party-type")
    val partyType:String,
    @ColumnInfo(name="addresses")
    val Addresses:List<AddressDetails>,
    @ColumnInfo(name="emails")
    val emails:List<String>,
    @ColumnInfo(name="phoneNumbers")
    val phoneNumbers: List<String>,
    @ColumnInfo(name="introducedBy")
    val introducedBy:String,
    @ColumnInfo(name="GSTN")
    val GSTN:String,
    @ColumnInfo(name="PAN")
    val PAN:String,
    @ColumnInfo(name="MSME")
    val MSME:String,
    @ColumnInfo("websites")
    val websites:List<String>,
    @ColumnInfo("remarks")
    val remarks:String
):Parcelable
