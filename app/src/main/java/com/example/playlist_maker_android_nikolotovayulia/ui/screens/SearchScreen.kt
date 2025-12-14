package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_nikolotovayulia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    val paddingDefault = dimensionResource(R.dimen.padding_default)
    val spacingMedium = dimensionResource(R.dimen.spacing_medium)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.search_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = paddingDefault)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { value -> searchQuery = value },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search_hint)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { /* пока ничего */ })
            )

            Spacer(Modifier.height(spacingMedium))

            Text(
                text = "Здесь позже появится список найденных треков",
                color = Color.Gray
            )
        }
    }
}
