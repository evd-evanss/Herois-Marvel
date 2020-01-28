package com.sayhitoiot.herismarvel.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.sayhitoiot.herismarvel.R
import com.sayhitoiot.marvelretrofit.model.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_heroes.view.*

class MarvelHeroiAdapter(private val character: MutableList<Result>):
    RecyclerView.Adapter<MarvelHeroiAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarvelHeroiAdapter.ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_list_heroes))
    }

    override fun onBindViewHolder(holder: MarvelHeroiAdapter.ViewHolder, position: Int) {
        holder.bind(character[position])
    }

    override fun getItemCount() = character.size

    fun updateList(character: MutableList<Result>){
        this.character.clear()
        this.character.addAll(character)
        notifyDataSetChanged()
    }




    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private lateinit var hero: Result

        fun bind(result: Result){
            this.hero = result
            Log.d("image", result.thumbnail.path)
            Picasso.get()
                .load((result.thumbnail.path + "." + result.thumbnail.extension).replace("http", "https"))
                .centerCrop()
                .fit()
                .error(R.drawable.ic_launcher_background)
                .into(itemView.image_thumbnail)
            itemView.text_name.text = result.name
        }

    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}