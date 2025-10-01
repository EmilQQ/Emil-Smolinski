package com.example.pamietnik.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pamietnik.data.EntryRepository
import com.example.pamietnik.data.IEntryRepository
import com.example.pamietnik.model.Entry

class EntryListViewModel : ViewModel() {
    private val repository: IEntryRepository = EntryRepository

    var state by mutableStateOf(listOf<Entry>())

    fun load(){
        state = repository.entries
    }

    fun addEntry(entry: Entry){
        repository.addEntry(entry)
    }

    fun removeEntry(entry: Entry){
        repository.removeEntry(entry)
    }

    fun updateEntry(entry: Entry){
        repository.updateEntry(entry)
    }

}