package com.sayhitoiot.herismarvel.repository

import com.sayhitoiot.marvelretrofit.api.OnGetMarvelCallback

interface MarvelRepository {
    //name: String, apiKey: String, ts: String, hash: String,
    fun getCharacter(offset: Int, limite: Int, callback: OnGetMarvelCallback)
}