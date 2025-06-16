package com.app.seequl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.RowItemNowShowingBinding
import com.app.seequl.helper.AppHelper
import javax.inject.Inject

class NowShowingAdapter @Inject constructor(
) : RecyclerView.Adapter<NowShowingAdapter.NowShowingViewHolder>() {

    private var mItems = arrayListOf<MovieDTO>()

    private var mListener: ((MovieDTO) -> Unit)? = null

    fun updateList(list: List<MovieDTO>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    fun addListener(listener: (MovieDTO) -> Unit) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowShowingViewHolder =
        NowShowingViewHolder(
            RowItemNowShowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: NowShowingViewHolder, position: Int) {
        holder.bind(mItems[position])
        holder.itemView.setOnClickListener {
            mListener?.invoke(mItems[position])
        }
    }

    override fun getItemCount(): Int = mItems.size

    inner class NowShowingViewHolder(private val binding: RowItemNowShowingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dto: MovieDTO) {
            binding.item = dto
        }
    }

}