package me.ruyeo.newcut.model.map

data class Latlng(
    val lat: Double,
    val lng: Double,
) {

    override fun toString(): String {
        return String.format("$lat,$lng")
    }

}
