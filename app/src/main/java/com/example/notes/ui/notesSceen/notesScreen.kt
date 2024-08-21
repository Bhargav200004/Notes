package com.example.notes.ui.notesSceen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.notes.ui.components.NoteAdd
import com.example.notes.util.SnackBarEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NoteScreenNav(navController: NavHostController) {

    val viewModel: NoteScreenViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()



    NoteScreen(
        state = state,
        snackBarEvent = viewModel.snackBarFlow,
        onEvent = viewModel::onEvent,
        navController = navController
    )
}

@Composable
private fun NoteScreen(
    state: NotesScreenStates,
    snackBarEvent: SharedFlow<SnackBarEvent>,
    onEvent: (NotesScreenEvent) -> Unit,
    navController: NavHostController
) {

    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackBarEvent.collectLatest { event ->
            when (event) {
                SnackBarEvent.NavigateUp -> {}
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHost.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }


    Scaffold(
        topBar = {
            NoteScreenTopBar(
                onSave = {
                    onEvent(NotesScreenEvent.SaveNote)
                    navController.navigateUp()
                },
                onBackButton = { navController.navigateUp() }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHost) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
        ) {
            NoteAdd(
                title = state.title,
                content = state.content,
                onTitleChange = { onEvent(NotesScreenEvent.OnTitleChange(it)) },
                onContentChange = { onEvent(NotesScreenEvent.OnContentChange(it)) }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteScreenTopBar(
    onSave: () -> Unit,
    onBackButton: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Notes", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = { onBackButton() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button Click"
                )
            }
        },
        actions = {
            IconButton(onClick = { onSave() }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "On Save"
                )
            }

        }
    )
}
