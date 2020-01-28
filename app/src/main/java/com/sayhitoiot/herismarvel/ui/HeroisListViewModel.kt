package com.sayhitoiot.herismarvel.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayhitoiot.herismarvel.repository.RepositoryDataManager
import com.sayhitoiot.herismarvel.repository.MarvelRepository
import com.sayhitoiot.marvelretrofit.api.OnGetMarvelCallback
import com.sayhitoiot.marvelretrofit.model.ReturnData

class HeroisListViewModel (private val repository: MarvelRepository = RepositoryDataManager())
    : ViewModel() {

    private val heroesList: MutableLiveData<ReturnData> = MutableLiveData()
    fun getHeroesList() = heroesList

    fun getHeroes(offset: Int, limite: Int) {
        repository.getCharacter(offset, limite, object : OnGetMarvelCallback {

            override fun onSuccess(marvelResponse: ReturnData) {
                Log.d("Reponse", "It's Ok!!!")
                heroesList.value = marvelResponse
            }

            override fun onError() {
                Log.e("ErrorViewModel", "Error in viewmodel call")
            }
        })
    }
}