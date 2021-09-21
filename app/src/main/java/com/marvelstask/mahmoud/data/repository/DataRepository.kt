package com.marvelstask.mahmoud.data.repository

import android.content.Context
import android.util.Log
import com.marvelstask.mahmoud.R
import com.marvelstask.mahmoud.data.database.AppDatabase
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel
import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicModel
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto
import com.marvelstask.mahmoud.data.database.response.MarvelBaseResponse
import com.marvelstask.mahmoud.data.network.ApiService
import com.marvelstask.mahmoud.data.network.NetworkClient
import com.marvelstask.mahmoud.utils.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository constructor(
    private val context: Context) {

    private val networkClient = NetworkClient.getInstance()
    private var database: AppDatabase = AppDatabase.getInstance(context)
    private val TAG: String = DataRepository::class.java.simpleName
    private val networkService: NetworkService = NetworkService(context)

    fun getComics(limit:Int, offset:Int, callback: RepositoryCallback<ComicsDto?>) {
            if(!networkService.isNetworkConnected()){
                callback.onError(context.getString(R.string.NO_INTERNET_CONNECTION ))
                return
            }

        networkClient.apiService().getComicsList(limit,offset).enqueue(object : Callback<MarvelBaseResponse<ComicsDto?>> {
                override fun onFailure(call: Call<MarvelBaseResponse<ComicsDto?>>, t: Throwable) {
                    callback.onError(t.message)
                }

                override fun onResponse(
                    call: Call<MarvelBaseResponse<ComicsDto?>>,
                    response: Response<MarvelBaseResponse<ComicsDto?>>
                ) {
                    if (response.isSuccessful) {
                        when(val marvelResponse = response.body()){
                            is MarvelBaseResponse<*> -> {
                                val comicsDto = marvelResponse.data as ComicsDto
                                callback.onSuccess(comicsDto)
                            }
                            else -> {
                                val errorMessage = "onResponse() response can't cast to it's specific type: call = [$call], response = [$response]"
                                Log.d(TAG, errorMessage )
                                callback.onError(errorMessage)
                            }
                        }
                    } else {
                        callback.onError(response.errorBody()?.string())
                    }
                }

            })

        }

    fun getCharacters(limit:Int, offset:Int, callback: RepositoryCallback<CharactersDto?>) {
        if(!networkService.isNetworkConnected()){
            callback.onError(context.getString(R.string.NO_INTERNET_CONNECTION ))
            return
        }
        networkClient.apiService().getCharactersList(limit, offset).enqueue(object : Callback<MarvelBaseResponse<CharactersDto?>> {
            override fun onFailure(call: Call<MarvelBaseResponse<CharactersDto?>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<MarvelBaseResponse<CharactersDto?>>,
                response: Response<MarvelBaseResponse<CharactersDto?>>
            ) {
                if (response.isSuccessful) {
                    when(val marvelResponse = response.body()){
                        is MarvelBaseResponse<*> -> {
                            val charactersDto = marvelResponse.data as CharactersDto
                            callback.onSuccess(charactersDto)
                        }
                        else -> {
                            val errorMessage = "onResponse() response can't cast to it's specific type: call = [$call], response = [$response]"
                            Log.d(TAG, errorMessage )
                            callback.onError(errorMessage)
                        }
                    }
                } else {
                    callback.onError(response.errorBody()?.string())
                }
            }

        })

    }

    fun saveComicsToDatabase(comicsList: MutableList<ComicModel>) {
        database.comicsDao().insertComicsList(comicsList)
    }

    fun saveCharactersToDatabase(charactersList: MutableList<CharacterModel>) {
        database.charactersDao().insertComicsList(charactersList)
    }


    interface RepositoryCallback<in T> {
        fun onSuccess(data: T?)
        fun onError(error : String?)
    }

}