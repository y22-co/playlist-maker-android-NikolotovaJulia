package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onNavigateBack: () -> Unit,
    onTrackClick: (Track) -> Unit = {}
) {
    val vm: PlaylistViewModel = viewModel()
    val favorites by vm.favoriteList.collectAsState(initial = emptyList())
    val padding = dimensionResource(R.dimen.padding_default)

    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.favorites_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_search_empty),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.no_favorites),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(padding)
            ) {
                items(favorites) { track ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = { onTrackClick(track) },
                                onLongClick = { trackToDelete = track }
                            )
                    ) {
                        TrackListItem(track = track)
                    }
                    HorizontalDivider(
                        thickness = dimensionResource(R.dimen.divider_thickness),
                        color = DividerDefaults.color
                    )
                }
            }
        }
    }

    trackToDelete?.let { t ->
        AlertDialog(
            onDismissRequest = { trackToDelete = null },
            confirmButton = {
                TextButton(onClick = {
                    vm.removeFromFavorites(t)
                    trackToDelete = null
                }) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { trackToDelete = null }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            title = { Text(stringResource(R.string.delete_from_favorites_title)) },
            text = { Text(stringResource(R.string.delete_from_favorites_confirm)) }
        )
    }
}


