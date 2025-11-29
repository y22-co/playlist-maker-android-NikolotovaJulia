package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_nikolotovayulia.R

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onNavigateBack: () -> Unit) {
    val paddingSettings = dimensionResource(R.dimen.padding_default_settings)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Плейлисты") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(paddingSettings)
        ) {
            Text("Здесь пока пусто")
        }
    }

}
