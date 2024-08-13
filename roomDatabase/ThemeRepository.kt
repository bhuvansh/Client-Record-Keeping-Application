package com.example.addressbook.roomDatabase

class ThemeRepository(private val themeDao: ThemeDao) {

    suspend fun insertColorSetting(theme: Theme) {
        themeDao.insertColorSettings(theme)
    }

    suspend fun updateColorSetting(theme: Theme) {
        themeDao.updateColorSettings(theme)
    }

    suspend fun getColorSetting(id: Int): Theme? {
        return themeDao.getColorSettings(id)
    }
}