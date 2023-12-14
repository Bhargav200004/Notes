package com.example.notes.ui.mainScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.notes.domain.model.Note
import com.example.notes.ui.components.NotesCard
import com.example.notes.util.Constant.NOTE_SCREEN_NAV

val note = listOf(
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
    Note(id = 1, title = "BHargav" , content = "ajhbfuhaufihasi"),
)


@Composable
fun HomeScreenNav(navController: NavHostController) {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        navController = navController,
    )



}

@Composable
private fun HomeScreen(
    state : HomeScreenState,
    onEvent : (HomeScreenEvent) -> Unit,
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            TopBarHomeScreen(
                onAddClick = { navController.navigate(route = NOTE_SCREEN_NAV)  }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        )
        {
            items(state.notes){notes->
                    NotesCard(
                        note = notes,
                        onDelete = {onEvent(HomeScreenEvent.DeleteNote)},
                        onEdit = { onEvent(HomeScreenEvent.SaveUpdateNote) },
                    )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHomeScreen(
    onAddClick : () -> Unit
) {
    TopAppBar(title = {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.headlineMedium
        )
    },
        actions = {
            IconButton(onClick =  onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "ADD Button")
            }
        }
    )
}

