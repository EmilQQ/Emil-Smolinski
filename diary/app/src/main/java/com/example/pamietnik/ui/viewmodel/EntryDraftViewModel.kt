package com.example.pamietnik.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pamietnik.data.EntryRepository
import com.example.pamietnik.data.IEntryRepository
import com.example.pamietnik.model.Entry

class EntryDraftViewModel : ViewModel() {
    private val repository: IEntryRepository = EntryRepository

    var state by mutableStateOf(listOf<Entry>())

    var id: Int = state.size + 1
    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var location: String = ""
    var photoUri by mutableStateOf<Uri?>(null)
    var audioUri: String? = null

    fun load(){
        state = repository.entries
    }

    fun updateTitle(tit: String) {
        title = tit
    }
    fun updateDescription(desc: String) {
        description = desc
    }
    fun updatePhotoUri(photo: Uri?) {
        photoUri = photo
    }
    fun updateAudioUri(path: String?) {
        audioUri = path
    }
    fun updateLocation(loc: String) {
        location = loc
    }

    fun fromEntry(entry: Entry) {
        title = entry.title
        description = entry.description
        photoUri = entry.photoUri
        audioUri = entry.audioUri
        location = entry.location
    }

}
