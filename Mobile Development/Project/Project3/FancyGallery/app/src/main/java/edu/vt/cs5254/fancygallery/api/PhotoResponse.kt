package edu.vt.cs5254.fancygallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Transfer Flickr Response into PhotoResponse
//stores photo objects -- list of photos
@JsonClass(generateAdapter = true)
data class PhotoResponse(
    @Json(name = "photo") val galleryItems: List<GalleryItem>
)