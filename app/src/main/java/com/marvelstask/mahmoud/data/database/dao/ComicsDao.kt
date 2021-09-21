package com.marvelstask.mahmoud.data.database.dao

import androidx.room.*
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel
import com.marvelstask.mahmoud.data.database.entinies.ComicModel
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto

@Dao
interface ComicsDao {
    @Query("select * from comics_table")
    fun selectAllComics(): List<ComicModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicsList(comicsList: MutableList<ComicModel>)

    @Delete
    fun deleteComic(comic: ComicModel)

}