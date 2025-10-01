package com.example.pamietnik.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pamietnik.data.EntryRepository
import com.example.pamietnik.data.IEntryRepository
import com.example.pamietnik.model.Entry

class EntryViewModel : ViewModel() {
    private val repository: IEntryRepository = EntryRepository

    var state by mutableStateOf<Entry?>(null)

    var location = mutableStateOf("")

    fun updateLocation(newLoc: String) {
        location.value = newLoc
    }

    fun load(id: Int) {
        state = repository.findById(id)
    }

    fun updateEntry(entry: Entry) {
        repository.updateEntry(entry)
    }
}