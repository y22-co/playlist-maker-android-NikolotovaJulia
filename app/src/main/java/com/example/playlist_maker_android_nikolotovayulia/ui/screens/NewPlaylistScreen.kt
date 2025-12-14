package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaylistScreen(
    navigateBack: () -> Unit,
    viewModel: PlaylistViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val paddingHorizontal = dimensionResource(R.dimen.padding_default_settings)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.new_playlist_title)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = paddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(dimensionResource(R.dimen.new_playlist_cover_top_spacing)))

            // Обложка (пока заглушка)
            val coverSize = dimensionResource(R.dimen.new_playlist_cover_size)
            Box(
                modifier = Modifier
                    .size(coverSize)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE0E0E0))
                    .clickable {
                        // пока заглушка
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.new_playlist_fields_top_spacing)))

            // Поля ввода
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.playlist_name_hint)) },
                singleLine = true
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text(stringResource(R.string.playlist_description_hint)) },
                singleLine = false
            )

            Spacer(modifier = Modifier.weight(1f))

            val buttonHeight = dimensionResource(R.dimen.new_playlist_button_height)
            val buttonCorner = dimensionResource(R.dimen.new_playlist_button_corner)

            Button(
                onClick = {
                    viewModel.insertNewPlaylist(name, description)
                    navigateBack()
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight),
                shape = RoundedCornerShape(buttonCorner)
            ) {
                Text(text = stringResource(R.string.save_playlist))
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
