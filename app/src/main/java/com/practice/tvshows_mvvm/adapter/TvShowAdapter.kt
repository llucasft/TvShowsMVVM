package com.practice.tvshows_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.practice.tvshows_mvvm.databinding.TvShowLayoutAdapterBinding
import com.practice.tvshows_mvvm.models.TvShowItem

class TvShowAdapter(
    private var tvShows: List<TvShowItem>
) : RecyclerView.Adapter<TvShowAdapter.MyViewHolder>() {

    private lateinit var clickListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        clickListener = listener
    }

    class MyViewHolder(
        val binding: TvShowLayoutAdapterBinding,
        listener: onItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = TvShowLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTvShow = tvShows[position]

        holder.binding.apply {
            textView.text = currentTvShow.name

            imageView.load(currentTvShow.image.original) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount() = tvShows.size

}




















