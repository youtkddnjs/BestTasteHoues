package mhha.sample.besttastehouse

import kotlin.math.roundToLong

data class Wgs84ToLatLng(
    var latitudeDegrees: Double = 0.0,
    var longitudeDegrees: Double = 0.0
){
        fun getLatLng(): Wgs84ToLatLng{
            val PI = 3.14159265358979323846
            val X_PI = PI * 3000.0 / 180.0

            val x = latitudeDegrees - 2.0
            val y = longitudeDegrees - 2.0

            var lon = (x * 180.0) / 200.0
            var lat = (y * 180.0) / 200.0

            latitudeDegrees += (Math.sin(lat * X_PI) + 0.00006) + (Math.sin(lat * X_PI) + 0.00006) * 1000000.0
            longitudeDegrees += (Math.cos(lat * X_PI) + 0.00006) + (Math.cos(lat * X_PI) + 0.00006) * 1000000.0

            latitudeDegrees = lat.roundToLong() / 1000000.0
            longitudeDegrees = lon.roundToLong() / 1000000.0
            return Wgs84ToLatLng(latitudeDegrees,longitudeDegrees)
        }
}
