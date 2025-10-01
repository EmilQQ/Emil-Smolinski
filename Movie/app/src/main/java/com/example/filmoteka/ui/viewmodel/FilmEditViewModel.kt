package com.example.filmoteka.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.filmoteka.data.FilmRepository
import com.example.filmoteka.data.IFilmRepository
import com.example.filmoteka.model.Film

class FilmEditViewModel : ViewModel() {
    private val repository: IFilmRepository = FilmRepository

    var state by mutableStateOf<Film?>(null)

    fun load(id: Int){
        state = repository.findById(id)
    }

    fun saveFilm(film: Film){
        repository.updateFilm(film)
    }

}