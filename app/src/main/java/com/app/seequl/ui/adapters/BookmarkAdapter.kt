package com.app.seequl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.seequl.data.model.BookmarkEntity
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.RowItemBookmarkBinding
import com.app.seequl.databinding.RowItemSearchMovieBinding
import javax.inject.Inject

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var mDeleteListener: ((BookmarkEntity) -> Unit)? = null

    private var mListener: ((BookmarkEntity) -> Unit)? = null

    private val items = mutableListOf<BookmarkEntity>()

    fun setItems(newItems: List<BookmarkEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()  // Notify entire dataset changed
    }


    fun addDeleteListener(listener: (BookmarkEntity) -> Unit) {
        mDeleteListener = listener
    }

    fun addListener(listener: (BookmarkEntity) -> Unit) {
        mListener = listener
    }

    inner class BookmarkViewHolder(private val binding: RowItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: BookmarkEntity) {
            binding.item = movie
            binding.executePendingBindings()

            binding.btnDelete.setOnClickListener {
                mDeleteListener?.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowItemBookmarkBinding.inflate(inflater, parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            mListener?.invoke(items[position])
        }
    }

    override fun getItemCount(): Int = items.size
}
