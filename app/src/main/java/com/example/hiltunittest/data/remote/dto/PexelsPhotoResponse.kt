package com.example.hiltunittest.data.remote.dto

/**
 * @author hafizdwp
 * 22/09/2023
 **/

data class PexelsPhotoResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>
)

data class Photo(
        val alt: String,
        val avg_color: String,
        val height: Int,
        val id: Int,
        val liked: Boolean,
        val photographer: String,
        val photographer_id: Int,
        val photographer_url: String,
        val src: Src,
        val url: String,
        val width: Int
)

data class Src(
    val landscape: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val original: String,
    val portrait: String,
    val small: String,
    val tiny: String
)