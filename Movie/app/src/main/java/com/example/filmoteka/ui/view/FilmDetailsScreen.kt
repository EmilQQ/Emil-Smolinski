package com.example.filmoteka.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.filmoteka.R
import com.example.filmoteka.data.FilmRepository
import com.example.filmoteka.model.Film
import com.example.filmoteka.model.Status
import com.example.filmoteka.ui.theme.FilmotekaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailsScreen(
    film: Film,
    onNavigationEdit: (Int) -> Unit,
    onNavigationUp: () -> Unit,
    onMarkAsWatched: () -> Unit,
    onSave: (Film) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    if (film.status == Status.OBEJRZANY) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = onNavigationUp)
                                .padding(12.dp),
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Powrot"
                        )
                    },
                )
            }
        ) { innerPaddings ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(bottom = 16.dp),
                    painter = rememberAsyncImagePainter(model = film.posterId),
                    contentDescription = null
                )
                Text(
                    text = film.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.premiere)} ${film.releaseDate}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.category)} ${film.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.status)} ${film.status}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if(film.rating != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${stringResource(R.string.rating)} ${film.rating} ${stringResource(R.string.ratingScale)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                if (film.comment != null && film.comment != "") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${stringResource(R.string.comment)} ${film.comment}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = onNavigationUp)
                                .padding(12.dp),
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Powrot"
                        )
                    },
                    actions = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = { onNavigationEdit(film.id) })
                                .padding(12.dp),
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edytuj"
                        )
                    }
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        val watchedFilm = film.copy(
                            status = Status.OBEJRZANY
                        )
                        onSave(watchedFilm)
                        onMarkAsWatched()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Oznacz jako obejrzany")
                }
            }
        ) { innerPaddings ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(bottom = 16.dp),
                    painter = rememberAsyncImagePainter(model = film.posterId),
                    contentDescription = null
                )
                Text(
                    text = film.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.premiere)} ${film.releaseDate}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.category)} ${film.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${stringResource(R.string.status)} ${film.status}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                if(film.rating != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${stringResource(R.string.rating)} ${film.rating}/10",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                if (film.comment != null && film.comment != "") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${stringResource(R.string.comment)} ${film.comment}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    FilmotekaTheme {
        FilmDetailsScreen(film = FilmRepository.films.get(1), {}, {}, {}, {})
    }
}