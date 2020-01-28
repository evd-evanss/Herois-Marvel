package com.sayhitoiot.herismarvel.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sayhitoiot.herismarvel.api.RetrofitClient
import com.sayhitoiot.marvelretrofit.api.MarvelApi
import com.sayhitoiot.marvelretrofit.api.OnGetMarvelCallback
import com.sayhitoiot.marvelretrofit.model.ReturnData
import com.sayhitoiot.marvelretrofit.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RepositoryDataManager: MarvelRepository {
    private var service: MarvelApi
    private var heroesData: MutableLiveData<ReturnData> = MutableLiveData()
    private var client = RetrofitClient()

    companion object {
        const val PUBLIC_KEY = Constants.Keys.PUBLIC_KEY
        const val PRIVATE_KEY = Constants.Keys.PRIVATE_KEY
    }

    init {
        service = client.webservice
    }

    override fun getCharacter(offset: Int, limite: Int, callback: OnGetMarvelCallback) {
        val ts = System.currentTimeMillis().toString() //Timestamp
        val hash = getMd5(ts)

        service.getCharacters(ts, Constants.Keys.PUBLIC_KEY, hash, limite, offset)
            .enqueue(object : Callback<ReturnData> {
                override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {

                    if (response.isSuccessful){
                        if (response.body() != null){
                            Log.i("Response", response.toString())
                            heroesData.postValue(response.body())
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onError()
                            Log.e("Response", " response null")
                        }

                    } else {
                        callback.onError()
                        Log.e("Response", response.raw().networkResponse().toString())
                    }

                }

                override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                    callback.onError()
                    t.printStackTrace()
                    Log.e("Response", javaClass.simpleName + " not response 2 " + t)
                }
            })
    }

    private fun getMd5(ts: String): String {
        try {

            val md = MessageDigest.getInstance("MD5")

            val messageDigest = md.digest(ts.toByteArray()
                    + PRIVATE_KEY.toByteArray()
                    + PUBLIC_KEY.toByteArray())

            val no = BigInteger(1, messageDigest)

            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            Log.i("md5", hashtext)

            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            Log.e("md5", e.toString())
            throw RuntimeException(e)
        }
    }
}