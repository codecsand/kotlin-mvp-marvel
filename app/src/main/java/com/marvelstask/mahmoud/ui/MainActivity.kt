package com.marvelstask.mahmoud.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marvelstask.mahmoud.R
import com.marvelstask.mahmoud.adapters.CharactersAdapter
import com.marvelstask.mahmoud.adapters.ComicsAdapter
import com.marvelstask.mahmoud.core.Constants
import com.marvelstask.mahmoud.data.database.AppDatabase
import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto
import com.marvelstask.mahmoud.data.network.ApiService
import com.marvelstask.mahmoud.data.repository.DataRepository
import com.marvelstask.mahmoud.presenter.MainContract
import com.marvelstask.mahmoud.presenter.MainPresenter
import com.marvelstask.mahmoud.utils.OnLoadMoreListener
import com.marvelstask.mahmoud.utils.RecyclerViewLoadMore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.comics_item.view.*


class MainActivity: AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter
    lateinit var comicsAdapter: ComicsAdapter
    lateinit var charactersAdapter: CharactersAdapter
    lateinit var loadMoreComicsListener: RecyclerViewLoadMore
    lateinit var loadMoreCharactersListener: RecyclerViewLoadMore
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        presenter = MainPresenter(this)
        presenter.initScreen()
    }

    override fun setupViews() {
        setupComicsView()
        setupCharactersView()
    }

    override fun setupComicsView() {
        comicsAdapter = ComicsAdapter(this)
        comics_recycler?.addItemDecoration(getItemDecoration())
        comics_recycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = comicsAdapter
        }
    }

    override fun setupCharactersView() {
        charactersAdapter = CharactersAdapter(this)
        characters_recycler.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = charactersAdapter
        }
    }

    override fun showComics(comicsDto: ComicsDto?) {
        comicsDto?.results?.let {
            if(it.isNotEmpty()){
                if(comicsAdapter.list.size < Constants.comicsLimit) {
                    comicsAdapter.setItems(it)
                }else{
                    comicsAdapter.addAllItems(it)
                    loadMoreComicsListener.setLoaded()
                }
            }else{
                Toast.makeText(
                    this,
                    getString(R.string.empty_list),
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
            removeWait()
        }
    }

    override fun showCharacters(charactersDto: CharactersDto?) {
        charactersDto?.results?.let {
            if(it.isNotEmpty()){
                if(charactersAdapter.list.size < Constants.charactersLimit) {
                    charactersAdapter.setItems(it)
                }else{
                    charactersAdapter.addAllItems(it)
                    loadMoreCharactersListener.setLoaded()
                }
            }else{
                Toast.makeText(
                    this,
                    getString(R.string.empty_list),
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
            removeWait()
        }
    }

    override fun setupListeners() {
        setComicsScrollListener()
        setCharactersScrollListener()
    }

    override fun setComicsScrollListener() {
        loadMoreComicsListener = RecyclerViewLoadMore(comics_recycler?.layoutManager as LinearLayoutManager,false)
        loadMoreComicsListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d(TAG,"load more Comics items")
                presenter.getAllComics()

            }
        })
        comics_recycler?.addOnScrollListener(loadMoreComicsListener)
    }

    override fun setCharactersScrollListener() {
        loadMoreCharactersListener = RecyclerViewLoadMore(characters_recycler?.layoutManager as LinearLayoutManager,true)
        loadMoreCharactersListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                Log.d(TAG,"load more characters data")
                presenter.getAllCharacters()
            }
        })
        characters_recycler?.addOnScrollListener(loadMoreCharactersListener)
    }

    private fun getItemDecoration():RecyclerView.ItemDecoration{
        val dividerItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.divider
            )!!
        )
        return dividerItemDecoration
    }


    override fun showWait() {
        loading?.visibility= View.VISIBLE
    }

    override fun removeWait() {
        loading?.visibility= View.GONE
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,message, android.widget.Toast.LENGTH_LONG)?.show()
    }





}