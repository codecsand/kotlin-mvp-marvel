package com.marvelstask.mahmoud.data.database.entinies

import com.google.gson.annotations.SerializedName

data class ComicsDto(
    @SerializedName("results") val results: MutableList<ComicModel>
)