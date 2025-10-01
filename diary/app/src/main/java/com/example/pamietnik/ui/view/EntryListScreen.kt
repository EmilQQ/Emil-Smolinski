package com.example.pamietnik.ui.view

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.pamietnik.R
import com.example.pamietnik.data.EntryRepository
import com.example.pamietnik.model.Entry
import com.example.pamietnik.ui.theme.PamietnikTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryListScreen(entries: List<Entry>, onEntryClicked: (Int) -> Unit, onAddEntry: () -> Unit){
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(
                            onClick = onAddEntry,
                            modifier = Modifier.padding(6.dp),
                        ) {
                            Icon(imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.add_entry)
                            )
                        }
                    }
                )
                    Spacer(modifier = Modifier.height(10.dp))
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
            items(entries.sortedBy { it.id }, key = { it.id }) { entry ->
                EntryView(entry, onClick = { onEntryClicked(entry.id) })
            }
        }
    }
}

@Composable
fun EntryView(entry: Entry, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row (
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ){
                Text(text = entry.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Text(text = entry.location,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmViewPreview() {
    PamietnikTheme {
        EntryListScreen(entries = EntryRepository.entries, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    PamietnikTheme {
        EntryView(entry = EntryRepository.entries[0], {})
    }
}