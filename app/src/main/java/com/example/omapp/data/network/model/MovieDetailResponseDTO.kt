package com.example.omapp.data.network.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponseDTO(
    @SerializedName("response") val response : MovieDTO
)
