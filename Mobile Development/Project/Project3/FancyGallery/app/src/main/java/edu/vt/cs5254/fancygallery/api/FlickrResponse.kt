package edu.vt.cs5254.fancygallery.api

import com.squareup.moshi.JsonClass

//stores photos object -- PhotoResponse
@JsonClass(generateAdapter = true)
data class FlickrResponse(
    val photos: PhotoResponse
)
