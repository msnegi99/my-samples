package com.msnegi.googlemapcontlocupdate.regularupdate

import java.io.Serializable

class LocData : Serializable {
    var latitude: String? = null
    var longitude: String? = null
    var altitude: String? = null
    var speed: String? = null
    var calSpeed: String? = null
    var distance: String? = null
    var shortestDistance: String? = null
    var angle: String? = null
    var date: String? = null
    var time: String? = null
    var timeGap: String? = null
    var gPRS: String? = null
    var battery_level: String? = null
    var accuracy: String? = null
}
