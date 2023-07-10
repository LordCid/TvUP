package com.example.omapp.domain.model


data class Movie(
    val id: Long,
    val externalId: String,
    val name: String,
    val description: String,
    val definition: String,
    val year: Int,
    val duration: Long,
    val imagesURL : List<String>,
    val genres : List<String>,
    val isFavorite : Boolean = false
)
