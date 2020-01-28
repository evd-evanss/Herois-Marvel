package com.sayhitoiot.herismarvel.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sayhitoiot.herismarvel.R
import com.sayhitoiot.marvelretrofit.model.Result
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var viewModel: HeroisListViewModel = HeroisListViewModel()
    private lateinit var adapter: MarvelHeroiAdapter
    private var offset: Int = 0
    private var limite: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(HeroisListViewModel::class.java)
        adapter = MarvelHeroiAdapter(mutableListOf())
        val layoutManager = LinearLayoutManager(this)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        recycler_view_characters.setLayoutManager(layoutManager)
        //recycler_view_characters.setLayoutManager(GridLayoutManager(this, 2))
        recycler_view_characters.itemAnimator = DefaultItemAnimator()
        recycler_view_characters.adapter = adapter
        viewModel.getHeroes(offset, limite)

        //getActionBar()?.setTitle("Hello world App");
        getSupportActionBar()?.setTitle("Heróis Marvel - "+ limite );

        swipeRefresh.setOnRefreshListener {
            limite= limite+3
            viewModel.getHeroes(offset, limite)
            val handler = Handler()
            handler.postDelayed({
                swipeRefresh.isRefreshing = false
            }, 2000)
        }
        configureObservers()

    }

    fun configureObservers(){
        viewModel.getHeroesList().observe(this, Observer { heroes ->
            heroes?.let {
                adapter.updateList(heroes.data.results as MutableList<Result>)
                Log.i("cont", heroes.data.count)
                Toast.makeText(this, "$limite itens adicionados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onSNACK(view: View){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, "$limite items adcionados",
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        snackbar.show()
    }
}
