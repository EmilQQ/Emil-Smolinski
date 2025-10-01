package com.example.filmoteka.data

import com.example.filmoteka.model.Film

object FilmRepository : IFilmRepository {
    private val _films: MutableList<Film>
    override val films: List<Film>
        get() = _films

    override fun findById(id: Int): Film? {
        return _films.find { it.id == id }
    }

    override fun addFilm(film: Film) {
        _films.add(film)
    }

    override fun removeFilm(id: Int) {
        val index = _films.indexOfFirst { it.id == id }
        _films.removeAt(index)
    }

    override fun updateFilm(film: Film) {
        val index = _films.indexOfFirst { it.id == film.id }
        if (index != -1) {
            _films[index] = film
        }
    }

    init {
        _films = sampleFilms.toMutableList()
    }

}