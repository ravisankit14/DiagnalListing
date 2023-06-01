package com.ravi.libapi.response

import com.google.gson.annotations.SerializedName
data class DiagnalResponse(
    val page: ListingData
)
data class ListingData(

    @SerializedName("title")
    val title: String,
    @SerializedName("total-content-items")
    val totalContentItems: String,
    @SerializedName("page-num")
    val pageNum: String,
    @SerializedName("page-size")
    val pageSize: String,
    @SerializedName("content-items")
    val contentItems: ContentResponse
)
