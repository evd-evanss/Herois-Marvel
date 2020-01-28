package com.sayhitoiot.marvelretrofit.api

import com.sayhitoiot.marvelretrofit.model.ReturnData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi{
    @GET("characters")
    fun getCharacters(@Query("ts") ts: String, // timestamp
                      @Query("apikey") apiKey: String,
                      @Query("hash")hash: String,
                      @Query("limit") limit: Int,
                      @Query("offset") offset: Int): Call<ReturnData>
}

interface OnGetMarvelCallback{
    fun onSuccess(marvelResponse: ReturnData)
    fun onError()
}