package com.example.filmoteka.ui.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    FilmotekaTheme {
        FilmAddNewScreen(films = FilmRepository.films, onSave = { }, onNavigationUp = { })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmAddNewScreen(
    films: List<Film>,
    onSave: (Film) -> Unit,
    onNavigationUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var titleError by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
//    var category by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var selectedImageId by remember { mutableStateOf<Uri?>(null) }
    var expandedCategory by remember { mutableStateOf(false) }
    var pickedCategory by remember { mutableStateOf<Category?>(null) }
    var categoryError by remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageId = uri
    }

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
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    var Error = false
                    if (title.isBlank()) {
                        titleError = true
                        Error = true
                    }
                    if (pickedCategory == null) {
                        categoryError = true
                        Error = true
                    } else {
                        val newFilm = Film(
                            id = films.size + 1,
                            title = title,
                            releaseDate = LocalDate.parse(releaseDate, dateFormatter),
                            category = pickedCategory ?: Category.FILM,
                            status = Status.valueOf(status),
                            posterId = selectedImageId
                                ?: Uri.parse("android.resource://com.example.filmoteka/${R.drawable.emptyimage}")
                        )
                        onSave(newFilm)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
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
                    .padding(bottom = 16.dp)
                    .clickable { launcher.launch("image/*") },
                painter = selectedImageId?.let { uri ->
                    rememberAsyncImagePainter(uri)
                } ?: rememberAsyncImagePainter(Uri.parse("android.resource://com.example.filmoteka/${R.drawable.emptyimage}")),
                contentDescription = null
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it
                                if(it.isNotBlank()){
                                    titleError = false
                                }},
                isError = titleError,
                supportingText = {
                    if (titleError) {
                        Text(text = stringResource(R.string.titleError), color = MaterialTheme.colorScheme.error)
                    }
                },
                label = { Text(stringResource(R.string.title)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = releaseDate,
                onValueChange = { releaseDate = it },
                label = { Text(stringResource(R.string.premiere)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = !expandedCategory }
            ) {
                OutlinedTextField(
                    value = pickedCategory?.name ?: stringResource(R.string.selectCategory),
                    onValueChange = {},
                    readOnly = true,
                    isError = categoryError,
                    supportingText = {
                        if (categoryError) {
                            Text(text = stringResource(R.string.categoryError), color = MaterialTheme.colorScheme.error)
                        }
                    },
                    label = {
                        Text(
                            stringResource(R.string.category),
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 12.sp
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                    modifier = Modifier
                        .menuAnchor()
                        .padding(bottom = 8.dp)
                )

                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }
                ) {
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
//            OutlinedTextField(
//                value = category,
//                onValueChange = { category = it },
//                label = { Text(stringResource(R.string.category)) },
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text(stringResource(R.string.status)) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}
