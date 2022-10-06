package com.cwsn.mobileapp.view.service

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.cwsn.mobileapp.utils.LoggerUtils

class GPSTracker : Service() {

    private var mContext: Context? = null

    private val TWO_MINUTES = 1000 * 60 * 15
    private val REFRESH_DELAY_TIME: Long = 8000
    private val MINIMUM_DISTANCE: Long = 0
    var locationManager: LocationManager? = null
    var intent: Intent? = null
    var listener: MyLocationListener? = null
    var previousBestLocation: Location? = null
    private var locIntent: Intent? = null

    companion object
    {
        val BROADCAST_ACTION = "location_update"
        var mDeviceLocation: Location? = null
    }

    init {

    }

    override fun onCreate() {
        super.onCreate()
        mContext=this@GPSTracker
        intent=Intent(BROADCAST_ACTION)
        mDeviceLocation = null
    }

    fun stopRunningService() {
        this.stopSelf()
        if (locIntent != null) {
            stopService(locIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locIntent = intent
        mDeviceLocation = null
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        locationManager?.getBestProvider(criteria, true)
        listener = MyLocationListener()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        )
        {
            return START_NOT_STICKY
        }
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            REFRESH_DELAY_TIME,
            MINIMUM_DISTANCE.toFloat(),
            listener!!
        )
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            REFRESH_DELAY_TIME,
            MINIMUM_DISTANCE.toFloat(),
            listener!!
        )
        return START_NOT_STICKY
    }

    protected fun isBetterLocation(location: Location, currentBestLocation: Location?): Boolean {
        LoggerUtils.error("TAG", "isBetterLocation")
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true
        }
        // Check whether the new location fix is newer or older
        val timeDelta = location.time - currentBestLocation.time
        val isSignificantlyNewer: Boolean =
            timeDelta > TWO_MINUTES
        val isSignificantlyOlder: Boolean =
            timeDelta < -TWO_MINUTES
        val isNewer = timeDelta > 0
        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false
        }
        // Check whether the new location fix is more or less accurate
        val accuracyDelta = (location.accuracy - currentBestLocation.accuracy).toInt()
        val isLessAccurate = accuracyDelta > 0
        val isMoreAccurate = accuracyDelta < 0
        val isSignificantlyLessAccurate = accuracyDelta > 200
        // Check if the old and new location are from the same provider
        val isFromSameProvider: Boolean = isSameProvider(
            location.provider,
            currentBestLocation.provider
        )

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true
        } else if (isNewer && !isLessAccurate) {
            return true
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true
        }
        return false
    }

    /** Checks whether two providers are the same  */
    private fun isSameProvider(provider1: String?, provider2: String?): Boolean {
        return if (provider1 == null) {
            provider2 == null
        } else provider1 == provider2
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager?.removeUpdates(listener!!)
        stopSelf()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    inner class MyLocationListener:LocationListener{
        override fun onLocationChanged(location: Location) {
            LoggerUtils.error("TAG", "onLocationChanged" + location.provider)
            if(isBetterLocation(location, previousBestLocation)) {
                mDeviceLocation =location
                intent?.putExtra("Latitude", location.latitude)
                intent?.putExtra("Longitude", location.longitude)
                intent?.putExtra("Provider", location.provider)
                LocalBroadcastManager.getInstance(mContext!!).sendBroadcast(intent!!)
                previousBestLocation = location
            }
            else{
                mDeviceLocation =location
                intent?.putExtra("Latitude", location.latitude)
                intent?.putExtra("Longitude", location.longitude)
                intent?.putExtra("Provider", location.provider)
                LocalBroadcastManager.getInstance(mContext!!).sendBroadcast(intent!!)
                previousBestLocation = location
            }
        }

    }
}