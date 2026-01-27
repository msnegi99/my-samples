package com.msnegi.googlemapcontlocupdate.firstupdate

import android.location.Location

interface LatLongReceived {
    fun onLocationReceived(loc: Location?)
}
