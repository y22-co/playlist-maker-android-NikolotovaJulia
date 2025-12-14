package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    playlist: Playlist?,
    navigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
        if (playlist == null) return@Scaffold

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.padding_default_settings))
        ) {
            // Обложка
            val coverSize = dimensionResource(R.dimen.new_playlist_cover_size)
            Box(
                modifier = Modifier
                    .size(coverSize)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_music),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            Text(
                text = playlist.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            if (!playlist.description.isNullOrBlank()) {
                Text(
                    text = playlist.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "${playlist.tracks.size} треков",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            LazyColumn {
                items(playlist.tracks) { track ->
                    PlaylistTrackRow(track = track, onClick = { onTrackClick(track) })
                    Divider(thickness = dimensionResource(R.dimen.divider_thickness))
                }
            }
        }
    }
}

@Composable
private fun PlaylistTrackRow(
    track: Track,
    onClick: () -> Unit
) {
    val iconSize = dimensionResource(R.dimen.icon_size)
    val rowPaddingV = dimensionResource(R.dimen.padding_vertical)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = rowPaddingV),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = track.artworkUrl100,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_music),
            error = painterResource(R.drawable.ic_music),
            modifier = Modifier.size(iconSize)
        )
        Spacer(Modifier.width(dimensionResource(R.dimen.spacing_medium)))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(track.trackName)
            Text(
                text = track.artistName,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(track.trackTime, style = MaterialTheme.typography.bodySmall)
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}
