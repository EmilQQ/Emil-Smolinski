package com.example.pamietnik.data

import com.example.pamietnik.model.Entry

interface IEntryRepository {
    val entries: List<Entry>

    fun findById(id: Int): Entry?
    fun addEntry(entry: Entry)
    fun removeEntry(entry: Entry)
    fun updateEntry(entry: Entry)
}