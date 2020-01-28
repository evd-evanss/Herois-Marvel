package com.sayhitoiot.herismarvel.api

import com.google.gson.GsonBuilder
import com.sayhitoiot.marvelretrofit.api.MarvelApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
        val webservice: MarvelApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/public/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(MarvelApi::class.java)
        }
}