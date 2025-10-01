package com.example.filmoteka

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmoteka.ui.theme.FilmotekaTheme
import com.example.filmoteka.ui.view.FilmAddNewScreen
import com.example.filmoteka.ui.view.FilmDetailsScreen
import com.example.filmoteka.ui.view.FilmListScreen
import com.example.filmoteka.ui.view.FilmEditScreen
import com.example.filmoteka.ui.view.FilmSetAsWatchedScreen
import com.example.filmoteka.ui.viewmodel.FilmEditViewModel
import com.example.filmoteka.ui.viewmodel.FilmListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmotekaTheme {
                Home()
            }
        }
    }
}

object Destinations {
    val argId = "id"
    val animeListDestination = "list"
    val animeDetailsDestination = "details/{id}"
    val animeEditDestination = "edit/{id}"
    val animeSetAsWatchedDestination = "setAsWatched/{id}"
    val animeAddNew = "addNew"

    fun getRouteForDetails(id: Int): String{
        return animeDetailsDestination.replace("{$argId}", id.toString())
    }

    fun getRouteForEdit(id: Int): String{
        return animeEditDestination.replace("{$argId}", id.toString())
    }

    fun getRouteForSetAsWatched(id: Int): String{
        return animeSetAsWatchedDestination.replace("{$argId}", id.toString())
    }

    fun getRouteForAddNew(): String{
        return animeAddNew
    }
}

@Composable
fun Home(){
    val navController = rememberNavController()
    NavHost(
        navController,
        Destinations.animeListDestination
    ){
        composable(Destinations.animeListDestination){
            val vm: FilmListViewModel = viewModel()

            LaunchedEffect(Unit) {
                vm.load()
            }

            FilmListScreen(
                films = vm.state,
                { navController.navigate(Destinations.getRouteForDetails(it)) },
                { navController.navigate(Destinations.getRouteForAddNew()) },
                onDeleteFilm = { filmId -> vm.deleteFilm(filmId) }
                )
        }

        composable(
            Destinations.animeDetailsDestination,
            arguments = listOf(
                navArgument(Destinations.argId){
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId)?: -1
            val vm: FilmEditViewModel = viewModel()

            LaunchedEffect(id) {
                vm.load(id)
            }
            val film = vm.state
            if (film != null) {
                FilmDetailsScreen(
                    film,
                    { navController.navigate(Destinations.getRouteForEdit(film.id)) },
                    {navController.popBackStack()},
                    {navController.navigate(Destinations.getRouteForSetAsWatched(film.id))},
                    onSave = {
                        vm.saveFilm(it)
                    }
                )
            }
        }

        composable(
            Destinations.animeEditDestination,
            arguments = listOf(
                navArgument(Destinations.argId){
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId)?: -1
            val vm: FilmEditViewModel = viewModel()

            LaunchedEffect(id) {
                vm.load(id)
            }
            val film = vm.state
            if (film != null) {
                FilmEditScreen(
                    film,
                    onSave = {
                        vm.saveFilm(it)
                        navController.popBackStack()
                    },
                    { navController.popBackStack() }
                )

            }}
        composable(
            Destinations.animeSetAsWatchedDestination,
            arguments = listOf(
                navArgument(Destinations.argId){
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId)?: -1
            val vm: FilmEditViewModel = viewModel()

            LaunchedEffect(id) {
                vm.load(id)
            }
            val film = vm.state
            if (film != null) {
                FilmSetAsWatchedScreen(
                    film,
                    onSave = {
                        vm.saveFilm(it)
                        navController.popBackStack()
                    }
                )

            }}
        composable(Destinations.animeAddNew){
            val vm: FilmListViewModel = viewModel()

            LaunchedEffect(Unit) {
                vm.load()
            }

            FilmAddNewScreen(
                films = vm.state,
                onSave = {
                    vm.saveFilm(it)
                    navController.navigate(Destinations.getRouteForDetails(it.id)){
                        popUpTo(Destinations.animeAddNew) {inclusive = true}
                    }
                },
                { navController.popBackStack() }
            )
        }
    }
}
