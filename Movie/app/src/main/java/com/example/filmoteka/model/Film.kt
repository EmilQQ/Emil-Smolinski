package com.example.filmoteka.model

import android.net.Uri
import java.time.LocalDate

data class Film(
    val id: Int,
    var title: String,
    var releaseDate: LocalDate,
    var category: Category,
    var status: Status,
    var posterId: Uri,
    var rating: Float? = null,
    var comment: String? = null
)