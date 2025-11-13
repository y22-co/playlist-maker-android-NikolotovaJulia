package com.example.playlist_maker_android_nikolotovayulia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_nikolotovayulia.navigation.PlaylistHost
import com.example.playlist_maker_android_nikolotovayulia.ui.theme.PlaylistMakerAndroidNikolotovaYulia
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeVm: ThemeViewModel = viewModel()
            PlaylistMakerAndroidNikolotovaYulia(
                darkTheme = themeVm.darkTheme.value,
                dynamicColor = true
            ) {
                PlaylistHost()
            }
        }
    }
}
