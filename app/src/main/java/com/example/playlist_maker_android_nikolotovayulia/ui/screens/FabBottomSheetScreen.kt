package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_nikolotovayulia.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FabBottomSheetScreen() {
    var showBottomSheet by remember { mutableStateOf(false) }

    val fabPadding = dimensionResource(R.dimen.fab_padding)
    val sheetHorizontal = dimensionResource(R.dimen.bottomsheet_horizontal_padding)
    val sheetIconSize = dimensionResource(R.dimen.bottomsheet_icon_size)
    val sheetIconTop = dimensionResource(R.dimen.bottomsheet_icon_top_padding)

    val sheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // кнопка по центру
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { showBottomSheet = true }
        ) {
            Text(text = stringResource(R.string.open_panel_button))
        }

        // плавающая кнопка внизу
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(fabPadding),
            onClick = { showBottomSheet = true },
            containerColor = Color.Gray,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.fab_description)
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = sheetHorizontal, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.bottomsheet_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .size(sheetIconSize)
                        .padding(top = sheetIconTop)
                )
            }
        }
    }
}
