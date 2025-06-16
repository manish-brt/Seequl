package com.app.seequl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.RowItemSearchMovieBinding
import javax.inject.Inject

class MovieSearchAdapter @Inject constructor() :
    RecyclerView.Adapter<MovieSearchAdapter.SearchViewHolder>() {

    private var mItems = arrayListOf<MovieDTO>()

    private var mListener: ((MovieDTO) -> Unit)? = null

    private var mBookmarkListener: ((MovieDTO, bookmarked: Boolean) -> Unit)? = null

    fun updateList(list: List<MovieDTO>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(dto: MovieDTO) {
        mItems.remove(dto)
        val pos = mItems.indexOf(dto)
        notifyItemRemoved(pos)
    }

    fun addListener(listener: (MovieDTO) -> Unit) {
        mListener = listener
    }

    fun addBookmarkedListener(listener: (MovieDTO, Boolean) -> Unit) {
        mBookmarkListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            RowItemSearchMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mItems[position])
        holder.itemView.setOnClickListener {
            mListener?.invoke(mItems[position])
        }
    }

    override fun getItemCount(): Int = mItems.size


    inner class SearchViewHolder(private val binding: RowItemSearchMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dto: MovieDTO) {
            binding.item = dto

            binding.bookmarkButton.setOnClickListener {
                mBookmarkListener?.invoke(dto, !dto.bookmarked)
                dto.bookmarked = !dto.bookmarked
                notifyItemChanged(adapterPosition)
            }
        }
    }
}