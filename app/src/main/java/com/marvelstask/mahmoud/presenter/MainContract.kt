package com.marvelstask.mahmoud.presenter

import com.marvelstask.mahmoud.data.database.entinies.CharacterModel
import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicModel
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto

interface MainContract {
    interface View {
        fun setupViews()
        fun setupComicsView()
        fun setupCharactersView()
        fun showComics(comicsDto: ComicsDto?)
        fun showCharacters(charactersDto: CharactersDto?)
        fun setupListeners()
        fun setComicsScrollListener()
        fun setCharactersScrollListener()
        fun showWait()
        fun removeWait()
        fun onFailure(message: String)
    }

    interface Presenter {
        fun initScreen()
        fun getAllComics()
        fun getAllCharacters()
        fun requestComicsData()
        fun requestCharactersData()
    }
}