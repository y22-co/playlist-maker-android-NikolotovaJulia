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
            .padding(20.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Поиск") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Очистить",
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

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { viewModel.search(searchQuery) },
            enabled = searchQuery.isNotBlank(),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Искать")
        }

        Spacer(Modifier.height(12.dp))

        when (screenState) {
            is SearchState.Initial -> {
                Text("Введите запрос для поиска треков")
            }

            is SearchState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).foundList
                if (tracks.isEmpty()) {
                    Text(text = "Ничего не найдено")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(tracks) { index, track ->
                            TrackListItem(track)
                            if (index < tracks.lastIndex) {
                                Divider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = 0.5.dp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                )
                            }
                        }
                    }
                }
            }
            is SearchState.Error -> {
                Text(text = "Ошибка: ${(screenState as SearchState.Error).error}")
            }
        }
    }
}

//@Composable
//fun TrackListItem(track: Track) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 12.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_music),
//            contentDescription = "Трек ${track.trackName}",
//            modifier = Modifier
//                .size(40.dp)
//                .padding(end = 8.dp)
//        )
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(text = track.trackName)
//            Text(text = track.artistName, style = MaterialTheme.typography.bodySmall)
//        }
//
//        Text(track.trackTime)
//    }
//}
