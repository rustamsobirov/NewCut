package me.ruyeo.newcut.model.detail

data class ReviewModel(
    var userImageUrl: String? = null,
    var userName: String? = null,
    var daysAgo: String? = null,
    var numStars: Double? = null,
    var review: String? = null
)