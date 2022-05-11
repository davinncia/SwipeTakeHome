package com.example.swipetakehome

data class Profile (
    val id: Int,
    val first_name: String,
    val last_name: String,
    val city: String,
    val country: String,
    val is_match: Boolean,
    val photos: List<String>
)