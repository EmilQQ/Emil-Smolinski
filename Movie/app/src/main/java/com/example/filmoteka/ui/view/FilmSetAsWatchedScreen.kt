package com.example.filmoteka.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.filmoteka.ui.theme.FilmotekaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmSetAsWatchedScreen(
    film: Film,
    onSave: (Film) -> Unit,
    modifier: Modifier = Modifier,
) {
    var rating by remember { mutableStateOf(film.rating ?: 2f) }
    var comment by remember { mutableStateOf(film.comment ?: "") }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        val editedFilm = film.copy(
                            rating = rating,
                            comment = comment,
                        )
                        onSave(editedFilm)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text("Zapisz")
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
                    text = stringResource(R.string.rating),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Ocena",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = stringResource(R.string.ratingScale),
                        style = MaterialTheme.typography.titleMedium
                    )
                }


                    Slider(
                        value = rating,
                        onValueChange = { rating = it },
                        valueRange = 1f..10f,
                        steps = 8,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )



                Spacer(modifier = Modifier.height(20.dp))


                Text(
                    text = stringResource(R.string.comment),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text(stringResource(R.string.comment)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    maxLines = 5
                )
            }
        }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    FilmotekaTheme {
        FilmSetAsWatchedScreen(film = FilmRepository.films.get(1), {})
    }
}