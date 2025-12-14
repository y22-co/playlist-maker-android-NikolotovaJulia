package com.example.playlist_maker_android_nikolotovayulia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nikolotovayulia.R
import com.example.playlist_maker_android_nikolotovayulia.domain.models.Track
import com.example.playlist_maker_android_nikolotovayulia.navigation.Screen
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
private fun TrackListItem(
    track: Track,
    onClick: () -> Unit
) {
    val coverSize = dimensionResource(R.dimen.icon_size) + 8.dp
    val rowPaddingV = dimensionResource(R.dimen.padding_vertical)
    val spacing = dimensionResource(R.dimen.spacing_medium)

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
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(coverSize)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.width(spacing))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = track.trackName)
            Row {
                Text(
                    text = track.artistName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = " · ${track.trackTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateBack: () -> Unit,
    navController: NavHostController,
    viewModel: SearchViewModel = viewModel()
) {
    val screenState by viewModel.allTracksScreenState.collectAsState()
    val searchQuery by viewModel.query.collectAsState()

    val paddingDefault = dimensionResource(R.dimen.padding_default)
    val spacingMedium = dimensionResource(R.dimen.spacing_medium)
    val dividerThickness = dimensionResource(R.dimen.divider_thickness)

    val playlistViewModel: PlaylistViewModel = viewModel()
    val scope = rememberCoroutineScope()

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
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = paddingDefault)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { value -> viewModel.onQueryChanged(value) },
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
                                viewModel.clearQuery()
                            }
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F3),
                    unfocusedContainerColor = Color(0xFFF0F0F3),
                    disabledContainerColor = Color(0xFFF0F0F3),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { viewModel.search() }
                )
            )

            Spacer(Modifier.height(spacingMedium))

            when (screenState) {
                is SearchState.Initial -> { }

                is SearchState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SearchState.EmptyResult -> {
                    // ничешго
                }

                is SearchState.Error -> {
                    val errorText = (screenState as SearchState.Error).error.orEmpty()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_search_error),
                            contentDescription = null
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(stringResource(R.string.server_error_title))
                        if (errorText.isNotBlank()) {
                            Text(
                                text = errorText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.retryLastSearch() }) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                is SearchState.Success -> {
                    val tracks = (screenState as SearchState.Success).foundList
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(tracks) { index, track ->
                            TrackListItem(
                                track = track,
                                onClick = {
                                    scope.launch {
                                        playlistViewModel.insertTrackToPlaylist(track, 0)
                                        navController.navigate("${Screen.TrackDetails.name}/${track.id}")
                                    }
                                }
                            )
                            if (index < tracks.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = dividerThickness,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

