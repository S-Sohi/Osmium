package com.example.learn.layout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learn.MainActivity
import com.example.learn.viewmodel.HomeViewModel
import com.example.learn.views.home.HomePage


val items = listOf(
    NavPage("Home", "home", Icons.Filled.Home),
    NavPage("Records", "records", Icons.Filled.List),
    NavPage("Cells", "cells", Icons.Filled.LocationOn)
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(context: Context, activity: MainActivity) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 24.dp, 0.dp, 0.dp), bottomBar = {
        BottomAppBar() {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                )
            }

        }
    }) {
        NavHost(navController, startDestination = "home", modifier = Modifier.padding(24.dp)) {
            val homeViewModel: HomeViewModel = HomeViewModel(context, activity)
            composable("home") {
                HomePage(modifier = Modifier, homeViewModel.state.value, homeViewModel::onEvent)
            }
            composable("records") { Button(onClick = {}) {} }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun BottomNavigationPreview() {
//    BottomNavigationBar()
//}