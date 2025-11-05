package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.playlist_maker_android_nikolotovayulia.R

@Composable
fun MainScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF3D6EFF),
                    RoundedCornerShape(
                        bottomStart = dimensionResource(R.dimen.corner_radius_bottom),
                        bottomEnd = dimensionResource(R.dimen.corner_radius_bottom)
                    )
                )
                .padding(
                    vertical = dimensionResource(R.dimen.padding_header_vertical),
                    horizontal = dimensionResource(R.dimen.padding_header_horizontal)
                )
        ) {
            Text(
                text = stringResource(R.string.app_name_title),
                color = Color.White,
                fontSize = dimensionResource(R.dimen.font_size_header).value.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_small)))

        DrawerItem(icon = Icons.Default.Search, text = stringResource(R.string.menu_search), onClick = onNavigateToSearch)
        DrawerItem(icon = Icons.Default.PlayArrow, text = stringResource(R.string.menu_playlists), onClick = {})
        DrawerItem(icon = Icons.Default.FavoriteBorder, text = stringResource(R.string.menu_favorites), onClick = {})
        DrawerItem(icon = Icons.Default.Settings, text = stringResource(R.string.menu_settings), onClick = onNavigateToSettings)
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(
                horizontal = dimensionResource(R.dimen.padding_item_horizontal),
                vertical = dimensionResource(R.dimen.padding_item_vertical)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.85f),
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_mainscreen))
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))

        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.9f),
            fontSize = dimensionResource(R.dimen.font_size_item).value.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
