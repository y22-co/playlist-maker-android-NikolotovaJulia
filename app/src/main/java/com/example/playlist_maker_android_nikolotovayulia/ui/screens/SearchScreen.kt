package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.creator.Creator
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit
) {
    val repository = remember { Creator.getTracksRepository() }

    var searchQuery by remember { mutableStateOf("") }
    var tracks by remember { mutableStateOf<List<Track>>(emptyList()) }

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
                keyboardActions = KeyboardActions(
                    onSearch = {
                        tracks = repository.searchTracks(searchQuery.trim())
                    }
                )
            )

            Spacer(Modifier.height(spacingMedium))

            if (tracks.isEmpty()) {
                Text(text = stringResource(R.string.nothing_found))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tracks) { track ->
                        TrackListItem(track = track)
                    }
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    val iconSize = dimensionResource(R.dimen.icon_size)
    val iconPaddingEnd = dimensionResource(R.dimen.icon_padding_end)
    val rowPaddingV = dimensionResource(R.dimen.padding_vertical)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = rowPaddingV),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
                .padding(end = iconPaddingEnd)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = track.trackName)
            Text(text = track.artistName, style = MaterialTheme.typography.bodySmall)
        }
        val minutes = track.trackTimeMillis / 1000 / 60
        val seconds = (track.trackTimeMillis / 1000) % 60
        Text(text = "%d:%02d".format(minutes, seconds))
    }
}
