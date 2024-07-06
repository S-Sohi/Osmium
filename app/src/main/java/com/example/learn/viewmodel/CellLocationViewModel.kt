package com.example.learn.viewmodel

import android.app.Activity
import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.example.learn.MainActivity
import com.example.learn.database.CellLocationDatabase
import com.example.learn.model.Cell
import com.example.learn.model.UserRecord
import com.example.learn.utils.CustomPhoneStateListener
import com.example.learn.utils.DetectedCellInfo
import com.example.learn.utils.LocationService

class CellLocationViewModel(private val context: Context, private val activity: MainActivity) {
    private val customPhoneStateListener: CustomPhoneStateListener =
        CustomPhoneStateListener(context)
    private val locationService: LocationService = LocationService(context, activity)
    private val db = CellLocationDatabase.getInstance(context)

    init {
        this.locationService.getPermission()
        val telephonyManager =
            this.context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(
            this.customPhoneStateListener,
            PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
        )
        telephonyManager.listen(this.customPhoneStateListener, PhoneStateListener.LISTEN_CELL_INFO)
    }

    fun inputsChanged() {
        val result = db.cellDao().getAllCells()
        val cellData = this.customPhoneStateListener.detectedCellInfo
        val cell: Cell = Cell(cellData.id, cellData.gen)
        db.cellDao().insert(cell)
//        val coordinates = this.locationService.getLocation()
//        db.cellDao().insert(cell)
//        val userRecord: UserRecord = UserRecord(
//            null,
//            coordinates!!.lat,
//            coordinates!!.lng,
//            cellData.signalPower,
//            cellData.id
//        )
//        db.userRecordDao().insert(userRecord)
    }
}