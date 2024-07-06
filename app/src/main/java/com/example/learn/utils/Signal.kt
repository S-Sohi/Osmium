package com.example.learn.utils

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.CellInfo
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoWcdma
import android.telephony.CellLocation
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import io.reactivex.subjects.PublishSubject


data class DetectedCellInfo(val gen:Int,val id:Int, val signalPower:Int){
}

class CustomPhoneStateListener(private val context: Context) : PhoneStateListener() {
    public lateinit var detectedCellInfo:DetectedCellInfo;
    public var signalChanged:PublishSubject<DetectedCellInfo> = PublishSubject.create()
    private var telephonyManager: TelephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager;
    init {
        detectedCellInfo = DetectedCellInfo(0,0,0)
    }
    public fun attachListener(){
        telephonyManager.listen(this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        telephonyManager.listen(this, PhoneStateListener.LISTEN_CELL_INFO);
    }

    @SuppressLint("MissingPermission")
    @Deprecated("Deprecated in Java")
    override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
        super.onSignalStrengthsChanged(signalStrength)
        val cellInfoList: List<CellInfo> = telephonyManager.allCellInfo
        if(cellInfoList.size == 0){

            return
        }
        var cellId:Int = 0
        var signalStrength:Int = 0
        var generation:Int = 0
        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoLte && cellInfo.isRegistered) {
                val cellIdentityLte = cellInfo.cellIdentity
                val cellSignalStrengthLte = cellInfo.cellSignalStrength
                cellId = cellIdentityLte.ci
                signalStrength = cellSignalStrengthLte.dbm
                generation = 4
                break
            }
            if(cellInfo is CellInfoWcdma){
                val cellIdentity = cellInfo.cellIdentity
                val cellSignalStrength = cellInfo.cellSignalStrength
                cellId = cellIdentity.cid
                signalStrength = cellSignalStrength.dbm
                generation = 3
            }
            if(cellInfo is CellInfoGsm){
                val cellIdentity = cellInfo.cellIdentity
                val cellSignalStrength = cellInfo.cellSignalStrength
                cellId = cellIdentity.cid
                signalStrength = cellSignalStrength.dbm
                generation = 2
            }
        }
        this.detectedCellInfo = DetectedCellInfo(generation,cellId,signalStrength)
        signalChanged.onNext(this.detectedCellInfo)

    }

    @SuppressLint("MissingPermission")
    override fun onCellInfoChanged(cellInfo: MutableList<CellInfo>?) {
        super.onCellInfoChanged(cellInfo)
        println(cellInfo)
    }

    @SuppressLint("MissingPermission")
    @Deprecated("Deprecated in Java")
    override fun onCellLocationChanged(location: CellLocation?) {
        super.onCellLocationChanged(location)
        println(location)
    }

}