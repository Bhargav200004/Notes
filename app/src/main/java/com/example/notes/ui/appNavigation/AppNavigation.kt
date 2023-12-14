package com.example.notes.ui.appNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.ui.mainScreen.HomeScreenNav
import com.example.notes.ui.notesSceen.NoteScreenNav
import com.example.notes.util.Constant.HOME_SCREEN_NAV
import com.example.notes.util.Constant.NOTE_SCREEN_NAV

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    
    NavHost(navController = navController, startDestination = HOME_SCREEN_NAV){
        composable(route =HOME_SCREEN_NAV){
            HomeScreenNav(navController)
        }
        composable(route = NOTE_SCREEN_NAV) {
            NoteScreenNav(navController)
        }
    }
}