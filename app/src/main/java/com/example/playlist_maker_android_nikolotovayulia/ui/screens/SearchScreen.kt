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
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
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
import com.example.playlist_maker_android_nikolotovayulia.ui.components.TrackListItem
import com.example.playlist_maker_android_nikolotovayulia.ui.state.SearchState
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.PlaylistViewModel
import com.example.playlist_maker_android_nikolotovayulia.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun TrackListItem(track: Track) {
    val coverSize = dimensionResource(R.dimen.track_image_size)
    val rowPaddingV = dimensionResource(R.dimen.padding_vertical)
    val spacing = dimensionResource(R.dimen.spacing_medium)

    Row(
        modifier = Modifier
            .fillMaxWidth()
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

        Column(modifier = Modifier.weight(1f)) {
            Text(track.trackName)
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
    val history by viewModel.history.collectAsState()


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
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF0F0F3),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp)
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
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
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

                    if (screenState is SearchState.Initial && history.isNotEmpty()) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            thickness = DividerDefaults.Thickness, color = Color(0xFFDDDDDD)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 4.dp)
                        ) {
                            history.take(3).forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.onQueryChanged(item)
                                            viewModel.search()
                                        }
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.History,
                                        contentDescription = null,
                                        tint = Color.Gray
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(text = item)
                                }
                            }
                        }
                    }
                }
            }

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
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = stringResource(R.string.server_error_message),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.retryLastSearch() }) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                is SearchState.Success -> {
                    val tracks = (screenState as SearchState.Success).tracks
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(tracks) { index, track ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            playlistViewModel.insertTrackToPlaylist(track, 0)
                                            navController.navigate("${Screen.TrackDetails.name}/${track.id}")
                                        }
                                    }
                            ) {
                                TrackListItem(track = track)
                            }
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

