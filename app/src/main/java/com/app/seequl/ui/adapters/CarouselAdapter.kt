package com.app.seequl.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.app.seequl.data.model.MovieDTO
import com.app.seequl.databinding.RowItemCarouselBinding
import javax.inject.Inject

class CarouselAdapter @Inject constructor(

) : PagerAdapter() {

    private val mImagesList = arrayListOf<MovieDTO>()

    private var mListener: ((MovieDTO) -> Unit)? = null

    fun updateList(list: List<MovieDTO>) {
        mImagesList.clear()
        mImagesList.addAll(list)
        notifyDataSetChanged()
    }

    fun addListener(listener: (MovieDTO) -> Unit) {
        mListener = listener
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = RowItemCarouselBinding.inflate(
            LayoutInflater.from(container.context), container, false
        )

        val dto = mImagesList[position % mImagesList.size]

        binding.apply {
            imageUrl = dto.backDropW500()
            title = dto.title
            rating = dto.rating()

            imageView.clipToOutline = true
            container.addView(binding.root)

            imageView.setOnClickListener { mListener?.invoke(dto) }

        }


        //For Infinite Scroll
//        if (position == mImagesList.size - 1) {
//            updateList(mImagesList)
//        }

        return binding.root
    }

    override fun getCount(): Int = if (mImagesList.size == 1) 1 else Int.MAX_VALUE

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return obj === view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val view: View = obj as View
        container.removeView(view)
    }
}