package com.example.notes.ui.mainScreen

import android.util.Log
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.notes.ui.appNavigation.Route
import com.example.notes.ui.components.DeleteDialog
import com.example.notes.ui.components.NotesCard
import com.example.notes.util.SnackBarEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenNav(navController: NavHostController) {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        snackBarEvent = viewModel.snackBarEventFlow,
        onEvent = viewModel::onEvent,
        navController = navController,
    )



}

@Composable
private fun HomeScreen(
    state : HomeScreenState,
    snackBarEvent : SharedFlow<SnackBarEvent>,
    onEvent : (HomeScreenEvent) -> Unit,
    navController: NavHostController
) {

    var isDeleteButton by rememberSaveable { mutableStateOf(false)}

    val snackBarHostState = remember{ SnackbarHostState()}


    DeleteDialog(
        isOpen =isDeleteButton,
        title = "Delete Note",
        bodyText = "Are you sure you want to delete note.",
        onDismissRequest = { isDeleteButton = false },
        onSaveRequest = {
            onEvent(HomeScreenEvent.DeleteNote)
            isDeleteButton = false
        }
    )

    LaunchedEffect(key1 = true){
        snackBarEvent.collectLatest {event->
            when(event){
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
                SnackBarEvent.NavigateUp -> {
                    navController.navigateUp()
                }
            }

        }
    }



    Scaffold(
        topBar = {
            TopBarHomeScreen(
                onAddClick = {
                    navController.navigate(route = Route.NoteScreen)
                }

            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)}
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
        )
        {
            items(state.notes){note->
                NotesCard(
                    note = note,
                    onDelete = {
                        onEvent(HomeScreenEvent.GetNoteById(id =
                        note.id ?: return@NotesCard))
                        isDeleteButton = true
                    },
                    onEdit = {
                        navController.navigate(Route.EditScreen(id = note.id.toString()))
                        Log.d("CheckingId" , note.id.toString())
                    },
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

