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
import com.marvelstask.mahmoud.data.database.entinies.ComicModel

class ComicsAdapter(private val context: Context) : RecyclerView.Adapter<ComicsAdapter.Holder>() {
    var list: MutableList<ComicModel> = arrayListOf()

    fun setItems(items: MutableList<ComicModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun addAllItems(items: MutableList<ComicModel>){
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
        val itemView = LayoutInflater.from(context).inflate(R.layout.comics_item, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = list[position].title
        holder.description.text = list[position].description
        //load comic item thumbnail image
        val thumbnailPath = "${list[position].thumbnail?.path}.${list[position].thumbnail?.extension}"
        Glide.with(context).load(thumbnailPath)
            //.override(Target.SIZE_ORIGINAL)
            .transition(DrawableTransitionOptions.withCrossFade()) //Here a fading animation
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class Holder(itemView: View ): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.comics_title)
        val description: TextView = itemView.findViewById(R.id.comics_description)
        val image: ImageView = itemView.findViewById(R.id.comics_image)
    }

}