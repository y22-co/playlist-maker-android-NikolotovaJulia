package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Long,
    onNavigateBack: () -> Unit,
    playlistViewModel: PlaylistViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var currentTrack by remember(trackId) {
        mutableStateOf<Track?>(playlistViewModel.getTrackById(trackId))
    }

    val playlists by playlistViewModel.playlists.collectAsState(initial = emptyList())
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (currentTrack == null) {
        LaunchedEffect(Unit) { onNavigateBack() }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                .padding(horizontal = dimensionResource(R.dimen.track_details_horizontal_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Обложка
            val coverSize = dimensionResource(R.dimen.track_cover_size)
            Box(
                modifier = Modifier
                    .size(coverSize)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_music),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            Text(
                text = currentTrack!!.trackName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = currentTrack!!.artistName,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.track_actions_top_spacing)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TrackActionButton(
                    icon = Icons.AutoMirrored.Filled.PlaylistAdd,
                    contentDescription = stringResource(R.string.add_to_playlist_title)
                ) {
                    showSheet = true
                }

                TrackActionButton(
                    icon = if (currentTrack!!.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_to_favorites)
                ) {
                    val track = currentTrack!!
                    val newFav = !track.favorite
                    scope.launch {
                        playlistViewModel.toggleFavorite(track, newFav)
                        currentTrack = track.copy(favorite = newFav)
                    }
                }
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.track_duration_top_spacing)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.duration_label),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = currentTrack!!.trackTime,
                    fontSize = 14.sp
                )
            }
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                AddToPlaylistBottomSheetContent(
                    playlists = playlists,
                    onSelect = { playlist ->
                        val track = currentTrack!!
                        scope.launch {
                            playlistViewModel.insertTrackToPlaylist(track, playlist.id)
                            showSheet = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TrackActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    val size = dimensionResource(R.dimen.track_action_button_size)
    val iconSize = dimensionResource(R.dimen.track_action_icon_size)

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color(0xFFE0E0E0))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = Color.DarkGray
        )
    }
}

@Composable
fun AddToPlaylistBottomSheetContent(
    playlists: List<Playlist>,
    onSelect: (Playlist) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.bottomsheet_horizontal_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_to_playlist_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))
        playlists.forEach { playlist ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(playlist) }
                    .padding(vertical = dimensionResource(R.dimen.padding_vertical)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = playlist.name, modifier = Modifier.weight(1f))
                Text(
                    text = "${playlist.tracks.size} треков",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
