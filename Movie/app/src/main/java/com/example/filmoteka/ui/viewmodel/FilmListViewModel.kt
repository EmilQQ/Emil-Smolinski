package com.example.filmoteka.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.filmoteka.data.FilmRepository
import com.example.filmoteka.data.IFilmRepository
import com.example.filmoteka.model.Film

class FilmListViewModel : ViewModel() {
    private val repository: IFilmRepository = FilmRepository

    var state by mutableStateOf(listOf<Film>())

    fun load(){
        state = repository.films
    }

    fun saveFilm(film: Film){
        repository.addFilm(film)
    }

    fun deleteFilm(id: Int){
        repository.removeFilm(id)
    }


}