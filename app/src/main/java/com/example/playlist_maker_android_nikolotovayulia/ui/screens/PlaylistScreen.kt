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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import com.example.playlist_maker_android_nikolotovayulia.ui.components.TrackListItem

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

            LazyColumn {
                items(playlist.tracks) { track ->
                    TrackListItem(
                        track = track,
                        onClick = { onTrackClick(track) }
                    )
                    HorizontalDivider(
                        thickness = dimensionResource(R.dimen.divider_thickness),
                        color = DividerDefaults.color
                    )
                }
            }

        }
    }
}
