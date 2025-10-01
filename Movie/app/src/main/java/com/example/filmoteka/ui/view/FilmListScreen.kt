package com.example.filmoteka.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.filmoteka.model.Category
import com.example.filmoteka.model.Film
import com.example.filmoteka.model.Status
import com.example.filmoteka.ui.theme.FilmotekaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(films: List<Film>, onFilmClicked: (Int) -> Unit, onAddFilm: () -> Unit, onDeleteFilm: (Int) -> Unit) {
    var pickedStatus by remember { mutableStateOf<Status?>(null) }
    var pickedCategory by remember { mutableStateOf<Category?>(null) }
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }

    val filteredFilms = films.filter { film ->
        (pickedStatus == null || film.status == pickedStatus) &&
                (pickedCategory == null || film.category == pickedCategory)
    }
    val filmsCount = filteredFilms.size

    var filmToDelete by remember { mutableStateOf<Film?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog && filmToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Usuwanie filmu") },
            text = { Text("Czy na pewno chcesz usunąć film ${filmToDelete?.title}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        filmToDelete?.let {
                            onDeleteFilm(it.id)
                        }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Tak")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Anuluj")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(
                            onClick = onAddFilm,
                            modifier = Modifier.padding(6.dp),
                        ) {
                            Icon(imageVector = Icons.Filled.Add,
                            contentDescription = "Dodaj film"
                            )
                        }
                    }
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text("${stringResource(R.string.filmsCount)} $filmsCount",
                        style = MaterialTheme.typography.titleMedium)
                    ExposedDropdownMenuBox(
                        expanded = expandedCategory,
                        onExpandedChange = { expandedCategory = !expandedCategory }
                    ) {
                        OutlinedTextField(
                            value = pickedCategory?.name ?: "${stringResource(R.string.selectCategory)}",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.category),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 12.sp)},
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                            modifier = Modifier
                                .menuAnchor()
                                .weight(1f)
                        )

                        DropdownMenu(
                            expanded = expandedCategory,
                            onDismissRequest = { expandedCategory = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.all)) },
                                onClick = {
                                    pickedCategory = null
                                    expandedCategory = false
                                }
                            )
                            Category.entries.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.name) },
                                    onClick = {
                                        pickedCategory = category
                                        expandedCategory = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandedStatus,
                        onExpandedChange = { expandedStatus = !expandedStatus }
                    ) {
                        OutlinedTextField(
                            value = pickedStatus?.name ?: "${stringResource(R.string.selectStatus)}",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.status),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 12.sp)},
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus) },
                            modifier = Modifier
                                .menuAnchor()
                                .weight(1f)
                        )

                        DropdownMenu(
                            expanded = expandedStatus,
                            onDismissRequest = { expandedStatus = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.all)) },
                                onClick = {
                                    pickedStatus = null
                                    expandedStatus = false
                                }
                            )
                            Status.entries.forEach { status ->
                                DropdownMenuItem(
                                    text = { Text(status.name) },
                                    onClick = {
                                        pickedStatus = status
                                        expandedStatus = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    ) { innerPaddings ->
        val systemPaddings = WindowInsets.systemBars.asPaddingValues()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(bottom = systemPaddings.calculateBottomPadding()),
            contentPadding = innerPaddings,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredFilms.sortedBy { it.releaseDate }, key = { it.id }) { film ->
                FilmView(film, onClick = { onFilmClicked(film.id) },
                    onLongClick = {
                        filmToDelete = film
                        showDeleteDialog = true
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmView(film: Film, onClick: () -> Unit, onLongClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable ( onClick = onClick, onLongClick = onLongClick ),
    ) {
        Row (
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                modifier = Modifier.size(90.dp),
                painter = rememberAsyncImagePainter(model = film.posterId),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ){
                Text(text = film.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Text(text = film.releaseDate.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
                Text(text = film.category.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                )
                Text(text = film.status.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FilmViewPreview() {
    FilmotekaTheme {
        FilmView(film = FilmRepository.films[0], {}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    FilmotekaTheme {
        FilmListScreen(films = FilmRepository.films, {}, {}, {})
    }
}