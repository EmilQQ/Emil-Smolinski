package com.example.filmoteka.data

import com.example.filmoteka.model.Film

interface IFilmRepository {
    val films: List<Film>

    fun findById(id: Int): Film?
    fun addFilm(film: Film)
    fun removeFilm(id: Int)
    fun updateFilm(film: Film)
}