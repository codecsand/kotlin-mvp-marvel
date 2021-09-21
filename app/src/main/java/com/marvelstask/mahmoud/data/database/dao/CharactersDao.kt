package com.marvelstask.mahmoud.data.database.dao

import androidx.room.*
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel

@Dao
interface CharactersDao {
    @Query("select * from characters_table")
    fun selectAllCharacters(): List<CharacterModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComicsList(charactersList: MutableList<CharacterModel>)

    @Delete
    fun deleteCharacter(character: CharacterModel)

}