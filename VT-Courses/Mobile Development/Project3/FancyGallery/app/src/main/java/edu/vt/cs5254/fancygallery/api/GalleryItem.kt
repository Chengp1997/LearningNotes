package edu.vt.cs5254.fancygallery.api

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Photo item -- A Response has a lot of galleryItem
@JsonClass(generateAdapter = true)
data class GalleryItem (
    val title: String,
    val id: String,
    @Json(name = "url_s") val url: String,
    val latitude: Double,
    val longitude: Double,
    val owner: String
    ){
    val photoPageUri: Uri
        get() = Uri.parse("https://www.flickr.com/photos/")
            .buildUpon()
            .appendPath(owner)
            .appendPath(id)
            .build()
}