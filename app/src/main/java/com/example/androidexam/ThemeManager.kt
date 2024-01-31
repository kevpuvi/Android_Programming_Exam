// package com.example.androidexam

//import android.provider.CalendarContract
//import androidx.compose.runtime.*
//import androidx.compose.material3.*
//import androidx.compose.ui.graphics.Color
//import com.example.androidexam.ThemeManager.Companion.darkColors
//import com.example.androidexam.ThemeManager.Companion.lightColors

//class ThemeManager {
//  private val _isDarkMode = mutableStateOf(false)

//  var isDarkMode: Boolean by _isDarkMode
//      private set

//  val colors: CalendarContract.Colors
//    @Composable get() = if (isDarkMode) darkColors else lightColors

//  fun toggleDarkMode() {
//      _isDarkMode.value = !_isDarkMode.value
//  }

//  companion object {
//      val lightColors: CalendarContract.Colors = lightColors(
//          primary = Color.Blue,
//          background = Color.White,
//          onBackground = Color.Black
//         // Define your light theme colors here
//      )
//      val darkColors: CalendarContract.Colors = darkColors(
//          primary = Color.Blue,
//          background = Color.Black,
//          onBackground = Color.White
//          // Define your dark theme colors here
//      )
//  }
//}
