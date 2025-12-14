package com.example.playlist_maker_android_nikolotovayulia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.playlist_maker_android_nikolotovayulia.navigation.PlaylistHost
import com.example.playlist_maker_android_nikolotovayulia.ui.theme.PlaylistMakerAndroidNikolotovaYulia

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistMakerAndroidNikolotovaYulia {
                PlaylistHost()
            }
        }
    }
}