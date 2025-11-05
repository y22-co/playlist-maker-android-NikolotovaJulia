package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nikolotovayulia.domain.Track
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.SearchViewModel
import androidx.compose.ui.res.painterResource
import com.example.playlist_maker_android_nikolotovayulia.R
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Трек ${track.trackName}",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = track.trackName)
            Text(text = track.artistName, style = MaterialTheme.typography.bodySmall)
        }

        Text(track.trackTime)
    }
}


@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    viewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
) {
    val screenState by viewModel.allTracksScreenState.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_default))
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(R.string.search_hint)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_hint)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_text),
                        modifier = Modifier.clickable {
                            searchQuery = ""
                            viewModel.search("")
                        }
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            )
        )

        Spacer(Modifier.height(dimensionResource(R.dimen.padding_vertical)))

        Button(
            onClick = { viewModel.search(searchQuery) },
            enabled = searchQuery.isNotBlank(),
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.button_padding_vertical))
        ) {
            Text(stringResource(R.string.search_button_text))
        }

        Spacer(Modifier.height(dimensionResource(R.dimen.padding_vertical)))

        when (screenState) {
            is SearchState.Initial -> {
                Text(stringResource(R.string.initial_message))
            }

            is SearchState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).foundList
                if (tracks.isEmpty()) {
                    Text(text = stringResource(R.string.nothing_found))
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(tracks) { index, track ->
                            TrackListItem(track)
                            if (index < tracks.lastIndex) {
                                Divider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = dimensionResource(R.dimen.divider_thickness),
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                )
                            }
                        }
                    }
                }
            }
            is SearchState.Error -> {
                Text(text = stringResource(R.string.error_message, (screenState as SearchState.Error).error))
            }
        }
    }
}
