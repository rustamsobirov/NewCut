package me.ruyeo.newcut.data.model

import me.ruyeo.newcut.utils.Constants.FILTER_DISTANCE

data class Criteria(
   val longitude : Double,
   val latitude: Double,
   val distance: Long = FILTER_DISTANCE
)
