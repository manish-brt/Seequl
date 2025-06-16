package com.app.seequl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.seequl.data.model.GenreDTO
import com.app.seequl.databinding.RowItemGenereBinding
import javax.inject.Inject

class GenereAdapter @Inject constructor(
) : RecyclerView.Adapter<GenereAdapter.GenereViewHolder>() {

    private var mItems = arrayListOf<GenreDTO>()

    fun updateList(list: List<GenreDTO>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenereViewHolder =
        GenereViewHolder(
            RowItemGenereBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: GenereViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    override fun getItemCount(): Int = mItems.size

    inner class GenereViewHolder(private val binding: RowItemGenereBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dto: GenreDTO) {
            binding.name = dto.name
        }
    }

}