package com.marvelstask.mahmoud.data.database.entinies

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comics_table")
data class ComicModel (
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("thumbnail")
    @Embedded
    var thumbnail:ComicThumbnail?
        ){
}

data class ComicThumbnail(
    val path:String?,
    val extension:String?){
}
