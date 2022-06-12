package me.ruyeo.newcut.data.model

import me.ruyeo.newcut.utils.Constants.FILTER_DISTANCE

data class Criteria(
   val longitude : Long,
   val latitude: Long,
   val distance: Long = FILTER_DISTANCE
)
