package com.marvelstask.mahmoud.data.database.entinies

import com.google.gson.annotations.SerializedName

data class CharactersDto(
    @SerializedName("results") val results: MutableList<CharacterModel>
)