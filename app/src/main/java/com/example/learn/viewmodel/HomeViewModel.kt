package com.example.learn.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.learn.MainActivity
import com.example.learn.database.CellLocationDatabase
import com.example.learn.model.Cell
import com.example.learn.model.UserRecord
import com.example.learn.utils.CustomPhoneStateListener
import com.example.learn.utils.DetectedCellInfo
import com.example.learn.utils.LocationCordinate
import com.example.learn.utils.LocationService
import io.reactivex.Observable


data class HomeState(
    var hasPermission: Boolean,
    var isConnected: Boolean,
    var isLoading: Boolean,
    var showGpsError: Boolean
)

enum class HomeEvent() {
    START, STOP
}

class HomeViewModel(context: Context, activity: MainActivity) : ViewModel() {
    var state = mutableStateOf(HomeState(false, false, false, false))
        private set

    var locationService: LocationService = LocationService(context, activity)
    var customPhoneStateListener: CustomPhoneStateListener = CustomPhoneStateListener(context)
    var db: CellLocationDatabase = CellLocationDatabase.getInstance(context)

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.START -> this.startMeasuring()
            HomeEvent.STOP -> this.stopMeasuring()
        }
    }

    @SuppressLint("CheckResult")
    fun startMeasuring() {
//        val coordinate = locationService.getLocation()
//        if (coordinate == null) {
//            return
//        }
        customPhoneStateListener.attachListener()
        customPhoneStateListener.signalChanged.subscribe{cellInformation ->
            val cell: Cell = Cell(cellInformation.id,cellInformation.gen)
            db.cellDao().insert(cell)
        }
//        Observable.combineLatest(locationService.locationChanged,customPhoneStateListener.signalChanged) { x1, x2 ->
//            listOf(
//                x1,
//                x2
//            )
//        }.subscribe{ x:List<Any> ->
//            state.value = state.value.copy(isLoading = true)
//            val cellInformation = x[0] as DetectedCellInfo
//            val data = x[1] as LocationCordinate
//            val cell: Cell = Cell(cellInformation.id,cellInformation.gen)
//        }

    }

    fun stopMeasuring() {
        state.value = state.value.copy(isLoading = false)
    }
}