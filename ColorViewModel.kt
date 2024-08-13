package com.example.addressbook

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.addressbook.roomDatabase.Graph
import com.example.addressbook.roomDatabase.Theme
import com.example.addressbook.roomDatabase.ThemeDao
import com.example.addressbook.roomDatabase.ThemeRepository
import kotlinx.coroutines.launch

class ColorViewModel(private val themeRepository: ThemeRepository = Graph.themeRepository) : ViewModel() {

    var itemHeadingColor by mutableStateOf(Color.Black)
    var itemSubHeadingBgColor by mutableStateOf(Color.DarkGray)
    var itemSubHeadingTextColor by mutableStateOf(Color.White)
    var itemValueTextColor by mutableStateOf(Color.Black)
    var itemValueBgColor by mutableStateOf(Color.Gray)
    var itemScreenColor by mutableStateOf(Color.White)

    init {
        loadColorSettings()
    }

    private fun loadColorSettings() {
        viewModelScope.launch {
            val settings = themeRepository.getColorSetting(id=0)
            settings?.let {
                itemHeadingColor = Color(it.itemHeadingColor)
                itemSubHeadingBgColor = Color(it.itemSubHeadingBgColor)
                itemSubHeadingTextColor = Color(it.itemSubHeadingTextColor)
                itemValueTextColor = Color(it.itemValueTextColor)
                itemValueBgColor = Color(it.itemValueBgColor)
                itemScreenColor = Color(it.itemScreenColor)
            }
        }
    }

    fun saveColorSettings() {
        viewModelScope.launch {
            val settings = Theme(
                itemHeadingColor = itemHeadingColor.toArgb(),
                itemSubHeadingBgColor = itemSubHeadingBgColor.toArgb(),
                itemSubHeadingTextColor = itemSubHeadingTextColor.toArgb(),
                itemValueTextColor = itemValueTextColor.toArgb(),
                itemValueBgColor = itemValueBgColor.toArgb(),
                itemScreenColor = itemScreenColor.toArgb()
            )
            themeRepository.insertColorSetting(settings)
        }
    }
}