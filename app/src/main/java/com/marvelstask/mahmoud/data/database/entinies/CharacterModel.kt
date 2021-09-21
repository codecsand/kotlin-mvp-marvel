package com.marvelstask.mahmoud.data.database.entinies

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters_table")
data class CharacterModel (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("thumbnail")
    @Embedded
    var thumbnail:CharacterThumbnail?
        ){
}

data class CharacterThumbnail(
    val path:String?,
    val extension:String?){
}
