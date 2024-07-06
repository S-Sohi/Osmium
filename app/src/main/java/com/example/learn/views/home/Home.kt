package com.example.learn.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learn.viewmodel.HomeEvent
import com.example.learn.viewmodel.HomeState

@Composable
fun HomePage(
    modifier: Modifier = Modifier, state: HomeState,
    event: (event: HomeEvent) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LargeFloatingActionButton(
                onClick = {
                    if (state.isLoading) {
                        event(HomeEvent.STOP)
                    }
                    else{
                        event(HomeEvent.START)
                    }
                },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    if (state.isLoading) {
                        Text(text = "Measuring...", color = MaterialTheme.colorScheme.secondary)
                    } else {
                        Text(text = "Start", color = MaterialTheme.colorScheme.secondary)
                    }
//                    Icon(Icons.Filled.Add, "Large floating action button")
                }

            }
            if (state.isLoading) {
                Text(
                    modifier = Modifier.padding(
                        20.dp
                    ),
                    text = "Press again to cancel",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Text(
                    modifier = Modifier.padding(
                        20.dp
                    ),
                    text = "Press start button to start measuring the incoming signal power",
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }


        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeView() {
//    HomePage()
//}