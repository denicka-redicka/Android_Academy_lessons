package com.example.androidacademymovie.presentation.moviedetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidacademymovie.R
import com.example.androidacademymovie.model.Actor

class ActorsListAdapter : ListAdapter<Actor, ActorsListAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.actor_item_holder, parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = getItem(position)
        holder.bind(actor)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val actorImage = itemView.findViewById<ImageView>(R.id.actor_image)
        private val actorName = itemView.findViewById<TextView>(R.id.actor_name)

        fun bind(item: Actor) {
            actorName.text = item.name
            actorImage.load(item.imageUrl)
        }

    }

    class DiffCallback: DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }

    }
}