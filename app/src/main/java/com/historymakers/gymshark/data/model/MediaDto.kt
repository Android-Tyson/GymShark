package com.historymakers.gymshark.data.model

import com.google.gson.annotations.SerializedName

data class MediaDto(
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String?,

    @SerializedName("alt")
    val alt: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("id")
    val id: Long,

    @SerializedName("position")
    val position: Int,

    @SerializedName("product_id")
    val productId: Long,

    @SerializedName("src")
    val src: String?,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("variant_ids")
    val variantIds: List<Any>,

    @SerializedName("width")
    val width: Int
)