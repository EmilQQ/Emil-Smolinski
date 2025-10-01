package com.example.pamietnik.data

import com.example.pamietnik.model.Entry

object EntryRepository : IEntryRepository {
    private val _entries: MutableList<Entry>
    override val entries: List<Entry>
        get() = _entries

    override fun findById(id: Int): Entry? {
        return _entries.find { it.id == id }
    }

    override fun addEntry(entry: Entry) {
        _entries.add(entry)
    }

    override fun removeEntry(entry: Entry) {
        _entries.remove(entry)
    }

    override fun updateEntry(entry: Entry) {
        val index = _entries.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            _entries[index] = entry
        }
    }

    init {
        _entries = sampleEntries.toMutableList()
    }
}