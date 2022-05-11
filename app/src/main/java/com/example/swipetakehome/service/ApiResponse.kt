package com.example.swipetakehome.service

import com.example.swipetakehome.Profile
import com.google.gson.annotations.SerializedName

data class ApiResponse(
   @SerializedName("profiles")
   val data: List<Profile>
)
