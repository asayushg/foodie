package ayush.foodie.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication :Application(){

    val isDarkTheme = mutableStateOf(false)

    fun toggleLightTheme(){
        isDarkTheme.value = !isDarkTheme.value
    }

}