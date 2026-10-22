package com.msnegi.locationdemo

import android.location.Location

interface LatLongReceived {
    fun onLocationReceived(loc: Location?)
}
