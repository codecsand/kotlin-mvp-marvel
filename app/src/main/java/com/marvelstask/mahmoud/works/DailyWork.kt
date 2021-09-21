package com.marvelstask.mahmoud.works

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.marvelstask.mahmoud.core.Constants
import com.marvelstask.mahmoud.data.database.entinies.CharactersDto
import com.marvelstask.mahmoud.data.database.entinies.ComicsDto
import com.marvelstask.mahmoud.data.network.ApiService
import com.marvelstask.mahmoud.data.repository.DataRepository
import kotlinx.coroutines.*


class DailyWork(var context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    val TAG: String = DailyWork::class.java.simpleName
    val dataRepository = DataRepository(context)
    var sharedDeferred: Deferred<Unit>? = null

    override fun doWork(): Result {
        Log.i(TAG, "request the data from Marvel api")
        val scope = CoroutineScope(Dispatchers.IO)
        runBlocking {
            requestComics(scope).await()
            requestCharacters(scope).await()
            return@runBlocking Result.success()
        }
        return Result.success()
    }

    @Synchronized
    fun requestComics(scope: CoroutineScope): Deferred<Unit> {
        val deferredToWait = sharedDeferred
        sharedDeferred = scope.async {
            deferredToWait?.await()
            dataRepository.getComics(Constants.comicsLimit,Constants.comicsOffset,
                object : DataRepository.RepositoryCallback<ComicsDto?> {
                override fun onSuccess(data: ComicsDto?) {
                    if (data?.results?.isNotEmpty() == true) {
                        dataRepository.saveComicsToDatabase(data.results)
                        Constants.comicsOffset += data.results.size
                        Log.i(TAG, "work onSuccess: comics saved to database: ${data.results}")
                    }
                }

                override fun onError(error: String?) {
                    Log.i(TAG, error.toString())
                }
            })
        }
        return sharedDeferred as Deferred<Unit>
    }

    @Synchronized
    fun requestCharacters(scope: CoroutineScope): Deferred<Unit> {
        val deferredToWait = sharedDeferred
        sharedDeferred = scope.async {
            deferredToWait?.await()
            dataRepository.getCharacters(
                Constants.charactersLimit, Constants.charactersOffset,
                object : DataRepository.RepositoryCallback<CharactersDto?> {
                override fun onSuccess(data: CharactersDto?) {
                    if (data?.results?.isNotEmpty() == true) {
                        dataRepository.saveCharactersToDatabase(data.results)
                        Constants.charactersOffset += data.results.size
                        Log.i(TAG, "work onSuccess: characters saved to database: ${data.results}")
                    }
                }

                override fun onError(error: String?) {
                    Log.i(TAG, error.toString())
                }
            })
        }
        return sharedDeferred as Deferred<Unit>
    }

}