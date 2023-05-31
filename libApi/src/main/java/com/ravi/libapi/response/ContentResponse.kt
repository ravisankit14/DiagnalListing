package com.ravi.libapi.response

import com.google.gson.annotations.SerializedName

data class ContentResponse(
    var content: ArrayList<Content>
)

data class Content(
    @SerializedName("name")
    var name: String,
    @SerializedName("poster-image")
    var posterImage: String
)
