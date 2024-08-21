package com.example.notes.ui.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.ui.editScreen.EditScreenNav
import com.example.notes.ui.mainScreen.HomeScreenNav
import com.example.notes.ui.notesSceen.NoteScreenNav

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen){
        composable<Route.HomeScreen>{
            HomeScreenNav(navController)
        }


        composable<Route.NoteScreen>{
            NoteScreenNav(navController)
        }

        composable<Route.EditScreen>{
            EditScreenNav(navController = navController)
        }
    }

}
