package com.example.pamietnik

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pamietnik.model.Entry
import com.example.pamietnik.ui.theme.PamietnikTheme
import com.example.pamietnik.ui.view.DrawOnImageScreen
import com.example.pamietnik.ui.view.EntryAddScreen
import com.example.pamietnik.ui.view.EntryDetailsScreen
import com.example.pamietnik.ui.view.EntryEditScreen
import com.example.pamietnik.ui.view.EntryListScreen
import com.example.pamietnik.ui.view.PinScreen
import com.example.pamietnik.ui.viewmodel.EntryDraftViewModel
import com.example.pamietnik.ui.viewmodel.EntryListViewModel
import com.example.pamietnik.ui.viewmodel.EntryViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PamietnikTheme {
                Home()
            }
        }
    }
}

object Destinations {
    val argId = "id"
    val entryListDestination = "list"
    val entryDetailsDestination = "details/{id}"
    val entryEditDestination = "edit/{id}"
    val entryAddDestination = "add"
    val drawImageDestination = "draw/{uri}/{mode}"
    val argUri = "uri"
    val argMode = "mode"
    val pinDestination = "pin"

    fun getRouteForDetails(id: Int): String{
        return entryDetailsDestination.replace("{$argId}", id.toString())
    }

    fun getRouteForEdit(id: Int): String{
        return entryEditDestination.replace("{$argId}", id.toString())
    }

    fun getRouteForAddNew(): String{
        return entryAddDestination
    }

    fun getRouteForDraw(uri: String, mode: String): String{
        var drawDest = drawImageDestination
        drawDest = drawDest.replace("{$argUri}", Uri.encode(uri))
        drawDest = drawDest.replace("{$argMode}", mode)
        return drawDest
    }
}

@Composable
fun Home(){
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Destinations.pinDestination
    ){
        composable(Destinations.pinDestination) {
            PinScreen(
                onPinVerified = { navController.navigate(Destinations.entryListDestination) {
                    popUpTo(Destinations.pinDestination) { inclusive = true }
                } }
            )
        }

        composable(Destinations.entryListDestination){
            val vm: EntryListViewModel = viewModel()

            LaunchedEffect(Unit) {
                vm.load()
            }

            EntryListScreen(
                entries = vm.state,
                { navController.navigate(Destinations.getRouteForDetails(it)) },
                { navController.navigate(Destinations.getRouteForAddNew()) }
            )
        }

        composable(
            Destinations.entryDetailsDestination,
            arguments = listOf(
                navArgument(Destinations.argId){
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId)?: -1
            val vm: EntryViewModel = viewModel()

            LaunchedEffect(id) {
                vm.load(id)
            }
            val entry = vm.state
            if (entry != null) {
                EntryDetailsScreen(
                    entry,
                    { navController.navigate(Destinations.getRouteForEdit(it)) },
                    {navController.popBackStack()},
                )
            }
        }

        composable(
            Destinations.entryEditDestination,
            arguments = listOf(
                navArgument(Destinations.argId){
                    type = NavType.IntType
                }
            )
        ) {backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId)?: -1
            val vm: EntryViewModel = viewModel()
            val draftVm: EntryDraftViewModel = viewModel()
            val listVm: EntryListViewModel = viewModel()
            listVm.load()

            LaunchedEffect(id) {
                vm.load(id)
            }
            val entryE = vm.state
            if (entryE != null) {
                LaunchedEffect(Unit) {
                    if (draftVm.title.isBlank() && draftVm.description.isBlank()) {
                        draftVm.fromEntry(entryE)
                    }
                }
                EntryEditScreen(
                    draft = draftVm,
                    onSave = {
                        val entry = Entry(
                            id = entryE.id,
                            title       = draftVm.title,
                            description = draftVm.description,
                            photoUri    = draftVm.photoUri,
                            audioUri    = draftVm.audioUri,
                            location    = draftVm.location
                        )
                        listVm.updateEntry(entry)
                        navController.popBackStack()
                    },
                    onNavigationUp = {
                        navController.popBackStack()
                    },
                    onDrawRequested = {
                        draftVm.photoUri?.let { uri ->
                            navController.navigate(Destinations.getRouteForDraw(uri.toString(), "edit"))
                        }
                    }
                )

            }}

        composable(Destinations.entryAddDestination) { backStack ->
            val draftVm: EntryDraftViewModel = viewModel()
            val listVm: EntryListViewModel = viewModel()
            listVm.load()

            EntryAddScreen(
                draft = draftVm,
                onSave = {
                    val entry = Entry(
                        id = listVm.state.size + 1,
                        title       = draftVm.title,
                        description = draftVm.description,
                        photoUri    = draftVm.photoUri,
                        audioUri    = draftVm.audioUri,
                        location    = draftVm.location
                    )
                    listVm.addEntry(entry)
                    navController.navigate(Destinations.getRouteForDetails(entry.id)) {
                        popUpTo(Destinations.entryAddDestination) { inclusive = true }
                    }
                },
                onNavigationUp = {
                    navController.popBackStack()
                },
                onDrawRequested = {
                    draftVm.photoUri?.let { uri ->
                        navController.navigate(Destinations.getRouteForDraw(uri.toString(), "add"))
                    }
                }
            )
        }

        composable(
            Destinations.drawImageDestination,
            arguments = listOf(
                navArgument(Destinations.argUri) {
                    type = NavType.StringType
                },
                navArgument(Destinations.argMode) {
                    type = NavType.StringType
                }
            )
        ) { backStack ->
            val mode = backStack.arguments?.getString(Destinations.argMode) ?: "add"
            val parentEntry = remember(backStack) {
                when (mode){
                    "edit" -> navController.getBackStackEntry(Destinations.entryEditDestination)
                    "add" -> navController.getBackStackEntry(Destinations.entryAddDestination)
                    else -> navController.getBackStackEntry(Destinations.entryAddDestination)
                }
            }
            val draftVm: EntryDraftViewModel = viewModel(parentEntry)
            val uriArg = Uri.parse(backStack.arguments!!.getString(Destinations.argUri)!!)
            val ctx = LocalContext.current

            DrawOnImageScreen(
                imageUri = uriArg,
                onSave = { editedBmp ->
                    val file = File(ctx.cacheDir, "edited_${System.currentTimeMillis()}.png")
                    FileOutputStream(file).use { out ->
                        editedBmp.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                    draftVm.photoUri = Uri.fromFile(file)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}