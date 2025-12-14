package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Playlist
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Long,
    navigateBack: () -> Unit,
    viewModel: PlaylistViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    // один StateFlow
    val trackState by viewModel.currentTrack.collectAsState()
    val playlists by viewModel.playlists.collectAsState(initial = emptyList())

    // грузим трек по id
    LaunchedEffect(trackId) {
        viewModel.loadTrack(trackId)
    }

    // если ещё не загрузился – ничего не рисуем
    val currentTrack = trackState ?: return

    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.track_details_horizontal_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val coverSize = dimensionResource(R.dimen.track_cover_size)
            Box(
                modifier = Modifier
                    .size(coverSize)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = currentTrack.artworkUrl100,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.ic_music),
                    error = painterResource(R.drawable.ic_music),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

            Text(
                text = currentTrack.trackName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = currentTrack.artistName,
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
                    icon = if (currentTrack.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.add_to_favorites)
                ) {
                    val newFav = !currentTrack.favorite
                    scope.launch {
                        viewModel.toggleFavorite(currentTrack, newFav)
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
                    text = currentTrack.trackTime,
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
                        scope.launch {
                            viewModel.insertTrackToPlaylist(currentTrack, playlist.id)
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
            .padding(dimensionResource(R.dimen.bottomsheet_horizontal_padding))
    ) {
        Text(
            text = stringResource(R.string.add_to_playlist_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.spacing_medium_settings)))

        playlists.forEach { playlist ->
            PlaylistListItem(
                playlist = playlist,
                onClick = { onSelect(playlist) }
            )
            HorizontalDivider(
                thickness = dimensionResource(R.dimen.divider_thickness),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}
