package com.example.learn

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learn.database.CellLocationDatabase
import com.example.learn.layout.BottomNavigationBar
import com.example.learn.ui.theme.LearnTheme
import com.example.learn.ui.theme.md_theme_dark_background
import com.example.learn.utils.LocationService
import com.example.learn.utils.CustomPhoneStateListener
import com.example.learn.utils.DetectedCellInfo
import com.example.learn.viewmodel.CellLocationViewModel
import kotlin.reflect.KFunction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        val context = this
        val activity = this
        setContent {
            LearnTheme {
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    BottomNavigationBar(context,activity)
                }
            }
        }
    }


}

@Composable
fun MyApp(cellLocationViewModel:CellLocationViewModel ,modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController, startDestination = "home") {
        composable("home") { StartScreen(cellLocationViewModel,navController) }
        composable("detail") { DetailScreen(cellLocationViewModel,navController) }
    }
}

@Composable
fun StartScreen(cellLocationViewModel:CellLocationViewModel,navController: NavController, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StartActivity(cellLocationViewModel,modifier = modifier)
            Button(onClick = { navController.navigate("detail") }) {
                Text(text = "جزئیات")
            }
        }
    }
}

@Composable
fun DetailScreen(cellLocationViewModel:CellLocationViewModel,navController: NavController, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StartActivity(cellLocationViewModel,modifier = modifier)
        }
    }
}

@Composable
fun Container(cellLocationViewModel: CellLocationViewModel,modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            StartActivity(cellLocationViewModel, modifier = modifier)
        }
    }
}

@Composable
fun CellInfo(cell: DetectedCellInfo, modifier: Modifier = Modifier) {
    Text(text = "شناسه : ${cell.id}")
    Text(text = "نسل : ${cell.gen}")
    Text(text = "توان دریافتی (dbm) : ${cell.signalPower}")
}

@Composable
fun StartActivity(cellLocationViewModel: CellLocationViewModel,modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context as MainActivity
    val locationService = LocationService(context, activity)

    ExtendedFloatingActionButton(
        onClick = { cellLocationViewModel.inputsChanged() },
        icon = { Icon(Icons.Filled.Build, "Extended floating action button.") },
        text = { Text(text = "محاسبه") },
        shape = CircleShape
    )

}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LearnTheme {
//        StartActivity()
//    }
//}