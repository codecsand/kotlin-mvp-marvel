package com.marvelstask.mahmoud.data.auth

import com.marvelstask.mahmoud.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class Authenticator : Interceptor {
    companion object {
        private const val QUERY_NAME_TIMESTAMP = "ts"
        private const val QUERY_NAME_APIKEY = "apikey"
        private const val QUERY_NAME_HASH = "hash"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = System.currentTimeMillis().toString()
        val hash = MD5Hash.generate(ts + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_PUBLIC_KEY)
        val currentRequest = chain.request()
        val url = currentRequest.url.newBuilder()
            .addQueryParameter(QUERY_NAME_TIMESTAMP, ts)
            .addQueryParameter(QUERY_NAME_APIKEY, BuildConfig.MARVEL_PUBLIC_KEY)
            .addQueryParameter(QUERY_NAME_HASH, hash).build()
        val newRequest = currentRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

    object MD5Hash {
        fun generate(s: String): String? {
            return try {
                val messageDigest = MessageDigest.getInstance("MD5")
                val bigInt = BigInteger(1, messageDigest.digest(s.toByteArray(Charsets.UTF_8)))
                return String.format("%032x", bigInt)
            } catch (e: NoSuchAlgorithmException) {
                null
            }
        }
    }
}