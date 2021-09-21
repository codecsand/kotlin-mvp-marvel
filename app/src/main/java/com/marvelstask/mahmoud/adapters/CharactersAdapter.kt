package com.marvelstask.mahmoud.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.marvelstask.mahmoud.R
import com.marvelstask.mahmoud.data.database.entinies.CharacterModel

class CharactersAdapter(private val context: Context) : RecyclerView.Adapter<CharactersAdapter.Holder>() {
    var list: MutableList<CharacterModel> = arrayListOf()

    fun setItems(items: MutableList<CharacterModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun addAllItems(items: MutableList<CharacterModel>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.characters_item, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.name.text = list[position].name
        //load character item thumbnail image
        val thumbnailPath = "${list[position].thumbnail?.path}.${list[position].thumbnail?.extension}"
        Glide.with(context).load(thumbnailPath)
            .override(Target.SIZE_ORIGINAL)
            .transition(DrawableTransitionOptions.withCrossFade()) //Here a fading animation
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(itemView: View ): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.characters_name)
        val image: ImageView = itemView.findViewById(R.id.characters_image)
    }

}