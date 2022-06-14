package com.example.androidacademymovie.model

data class Movie(
    val id: Int,
    val name: String,
    val isLiked: Boolean,
    val countReviews: Int,
    val runningTime: Int,
    val imageUrl: String?,
    val pgAge: Int,
    val genre: List<Genre>,
    val rating: Int
        )