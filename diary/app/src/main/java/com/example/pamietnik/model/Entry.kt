package com.example.pamietnik.model

import android.net.Uri

data class Entry (
    val id: Int,
    var title: String,
    var description: String,
    var location: String,
    var photoUri: Uri? = null,
    var audioUri: String? = null
)