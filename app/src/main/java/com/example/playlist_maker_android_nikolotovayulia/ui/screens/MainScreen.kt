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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToPlaylists: () -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val paddingDefault = dimensionResource(R.dimen.padding_default)
    val spacingSmall = dimensionResource(R.dimen.spacing_small)
    val cornerRadiusBottom = dimensionResource(R.dimen.corner_radius_bottom)
    val paddingHeaderV = dimensionResource(R.dimen.padding_header_vertical)
    val paddingHeaderH = dimensionResource(R.dimen.padding_header_horizontal)
    val paddingItemH = dimensionResource(R.dimen.padding_item_horizontal)
    val paddingItemV = dimensionResource(R.dimen.padding_item_vertical)
    val iconSize = dimensionResource(R.dimen.icon_size_mainscreen)
    val fontSizeHeader = dimensionResource(R.dimen.font_size_header).value
    val fontSizeItem = dimensionResource(R.dimen.font_size_item).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF3D6EFF),
                    RoundedCornerShape(bottomStart = cornerRadiusBottom, bottomEnd = cornerRadiusBottom)
                )
                .padding(vertical = paddingHeaderV, horizontal = paddingHeaderH)
        ) {
            Text(
                text = "Playlist maker",
                color = Color.White,
                fontSize = fontSizeHeader.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(spacingSmall))

        DrawerItem(
            icon = Icons.Default.Search,
            text = "Поиск",
            iconSize = iconSize,
            paddingH = paddingItemH,
            paddingV = paddingItemV,
            fontSizeSp = fontSizeItem.sp,
            onClick = onNavigateToSearch
        )
        DrawerItem(
            icon = Icons.Default.PlayArrow,
            text = "Плейлисты",
            iconSize = iconSize,
            paddingH = paddingItemH,
            paddingV = paddingItemV,
            fontSizeSp = fontSizeItem.sp,
            onClick = onNavigateToPlaylists
        )
        DrawerItem(
            icon = Icons.Default.FavoriteBorder,
            text = "Избранное",
            iconSize = iconSize,
            paddingH = paddingItemH,
            paddingV = paddingItemV,
            fontSizeSp = fontSizeItem.sp,
            onClick = onNavigateToFavorites
        )
        DrawerItem(
            icon = Icons.Default.Settings,
            text = "Настройки",
            iconSize = iconSize,
            paddingH = paddingItemH,
            paddingV = paddingItemV,
            fontSizeSp = fontSizeItem.sp,
            onClick = onNavigateToSettings
        )
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    iconSize: Dp,
    paddingH: Dp,
    paddingV: Dp,
    fontSizeSp: TextUnit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = paddingH, vertical = paddingV),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.85f),
            modifier = Modifier.size(iconSize)
        )
        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.9f),
            fontSize = fontSizeSp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}