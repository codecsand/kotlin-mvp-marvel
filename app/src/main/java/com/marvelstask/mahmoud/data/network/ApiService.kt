package com.marvelstask.mahmoud.data.network


import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto
import com.marvelstask.mahmoud.data.database.response.MarvelBaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("comics")
    fun getComicsList(
        @Query("limit") limit: Int = 0,
        @Query("offset") offset: Int = 0
    ): Call<MarvelBaseResponse<ComicsDto?>>

    @GET("characters")
    fun getCharactersList(
        @Query("limit") limit: Int = 0,
        @Query("offset") offset: Int = 0
    ): Call<MarvelBaseResponse<CharactersDto?>>


}