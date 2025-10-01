package com.example.filmoteka.data

import android.net.Uri
import com.example.filmoteka.model.Category
import com.example.filmoteka.model.Film
import com.example.filmoteka.model.Status
import java.time.LocalDate
import com.example.filmoteka.R

val sampleFilms = listOf(
    Film(
        id = 1,
        title = "Incepcja",
        releaseDate = LocalDate.of(2010, 7, 16),
        category = Category.FILM,
        status = Status.OBEJRZANY,
        posterId = Uri.parse("android.resource://com.example.filmoteka/${R.drawable.incepcja}"),
        rating = 9.0f,
        comment = "Rewelacyjny film sci-fi."
    ),
    Film(
        id = 2,
        title = "Matrix",
        releaseDate = LocalDate.of(2011, 1, 18),
        category = Category.FILM,
        status = Status.NIEOBEJRZANY,
        posterId = Uri.parse("android.resource://com.example.filmoteka/${R.drawable.matrix}"),
        rating = null,
        comment = null
    ),
    Film(
        id = 3,
        title = "Titanic",
        releaseDate = LocalDate.of(2021, 3, 16),
        category = Category.SERIAL,
        status = Status.OBEJRZANY,
        posterId = Uri.parse("android.resource://com.example.filmoteka/${R.drawable.titanic}"),
        rating = 5.0f,
        comment = null
    ),
    Film(
        id = 4,
        title = "Asterix i Obelix",
        releaseDate = LocalDate.of(2001, 9, 16),
        category = Category.DOKUMENT,
        status = Status.OBEJRZANY,
        posterId = Uri.parse("android.resource://com.example.filmoteka/${R.drawable.asterixiobelix}"),
        rating = 10.0f,
        comment = null
    )
)