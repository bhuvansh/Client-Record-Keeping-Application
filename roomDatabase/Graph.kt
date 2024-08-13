package com.example.addressbook.roomDatabase

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Graph {
    lateinit var database:CompanyDatabase

    val companyRepository by lazy {
        CompanyRepository(companyDao = database.companyDao())
    }

    val themeRepository by lazy {
        ThemeRepository(themeDao = database.themeDao())
    }

//    val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            // Example migration code
//            database.execSQL("ALTER TABLE theme ADD COLUMN newColumn INTEGER NOT NULL DEFAULT 0")
//        }
//    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context,
            CompanyDatabase::class.java,
            "companyList.db")
//            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }
}