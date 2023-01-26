package com.practice.tvshows_mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.practice.tvshows_mvvm.databinding.TvShowFavoriteLayoutAdapterBinding
import com.practice.tvshows_mvvm.models.TvShowDetail
import com.practice.tvshows_mvvm.util.limitSummary
import com.practice.tvshows_mvvm.util.removeHtmlTags

class TvShowsFavoriteAdapter(
    private var tvShows: List<TvShowDetail>
) : RecyclerView.Adapter<TvShowsFavoriteAdapter.MyViewHolder>() {

    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    class MyViewHolder(
        val binding: TvShowFavoriteLayoutAdapterBinding,
        listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = TvShowFavoriteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTvShow = tvShows[position]

        holder.binding.apply {
            tvShowNameFavorite.text = currentTvShow.name
            tvShowRatingsFavorite.text = currentTvShow.rating.average.toString()
            tvShowSummaryFavorite.text = currentTvShow.summary.removeHtmlTags().limitSummary(100)
            imageViewFavorite.load(currentTvShow.image.original) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount() = tvShows.size

    fun getTvShowPosition(position: Int): TvShowDetail {
        return tvShows[position]
    }

}




















