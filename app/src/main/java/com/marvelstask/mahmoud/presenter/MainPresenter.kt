package com.marvelstask.mahmoud.presenter


import android.content.Context
import com.marvelstask.mahmoud.core.Constants
import com.marvelstask.mahmoud.data.database.AppDatabase
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel
import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicModel
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto
import com.marvelstask.mahmoud.data.repository.DataRepository
import com.marvelstask.mahmoud.utils.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPresenter constructor(private val context: Context) : MainContract.Presenter {

    private var _view: MainContract.View? = context as MainContract.View?
    private val database = AppDatabase.getInstance(context)
    private val networkService = NetworkService(context)
    private var dataRepository:DataRepository? = DataRepository(context)
    var list = arrayListOf<ComicModel>()
    private val TAG = MainPresenter::class.java.simpleName

    override fun initScreen() {
        _view?.setupViews()
        _view?.setupListeners()
        getAllComics()
        getAllCharacters()
    }

    override fun getAllComics() {
        if (_view != null) {
            _view?.showWait()
        }
        val data = database.comicsDao().selectAllComics()
        //setup initial comics offset
        Constants.comicsOffset = data.size
        //request the data by api request if the local is empty
        if (data.isEmpty()) {
            requestComicsData()
        } else {
            //if the internet connection is not available //load the local data
            if (!networkService.isNetworkConnected()) {
                _view?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        _view?.showComics(ComicsDto(data as MutableList<ComicModel>))
                    }
                }
            } else {
                requestComicsData()
            }
        }
    }

    override fun getAllCharacters() {
        if (_view != null) {
            _view?.showWait()
        }
        val data = database.charactersDao().selectAllCharacters()
        Constants.charactersOffset = data.size
        if (data.isEmpty()) {
            requestCharactersData()
        } else {
            if (!networkService.isNetworkConnected()) {
                _view?.let {
                    CoroutineScope(Dispatchers.Main).launch {
                        _view?.showCharacters(CharactersDto(data as MutableList<CharacterModel>))
                    }
                }
            } else {
                requestCharactersData()
            }
        }
    }

    override fun requestComicsData() {
        CoroutineScope(Dispatchers.IO).launch {
            dataRepository?.getComics(Constants.comicsLimit,Constants.comicsOffset,object : DataRepository.RepositoryCallback<ComicsDto?> {
                override fun onSuccess(data: ComicsDto?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (_view != null) {
                            _view?.removeWait()
                            _view?.showComics(data)
                            //save data to database
                            data?.results?.let {
                                dataRepository?.saveComicsToDatabase(it)
                                Constants.comicsOffset += it.size
                            }
                        }
                    }
                }

                override fun onError(error: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (_view != null) {
                            _view?.removeWait()
                            error?.let { _view?.onFailure(it) }
                        }
                    }
                }
            })
        }
    }

    override fun requestCharactersData() {
        CoroutineScope(Dispatchers.IO).launch {
            dataRepository?.getCharacters(Constants.charactersLimit,Constants.charactersOffset,object : DataRepository.RepositoryCallback<CharactersDto?> {
                override fun onSuccess(data: CharactersDto?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (_view != null) {
                            _view?.removeWait()
                            _view?.showCharacters(data)
                            data?.results?.let {
                                dataRepository?.saveCharactersToDatabase(it)
                                Constants.charactersOffset += it.size
                            }
                        }
                    }
                }

                override fun onError(error: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (_view != null) {
                            _view?.removeWait()
                            error?.let { _view?.onFailure(it) }
                        }
                    }
                }
            })
        }
    }


}