package com.example.swipetakehome

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.swipetakehome.databinding.ItemSwipeCardBinding

class CardStackAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Profile> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSwipeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CardViewHolder) holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class CardViewHolder(private val binding: ItemSwipeCardBinding): RecyclerView.ViewHolder(binding.root) {

        private var gallery = listOf<String>()
        private var galleryPosition = 0 // Picture to display from user's gallery

        init {
            binding.leftClickZone.setOnClickListener {
                if (galleryPosition <= 0) return@setOnClickListener

                displayPicture(gallery[--galleryPosition])
            }

            binding.rightClickZone.setOnClickListener {
                if (galleryPosition >= gallery.lastIndex) return@setOnClickListener

                displayPicture(gallery[++galleryPosition])
            }
        }

        fun bind(profile: Profile) {
            binding.mainText.text = profile.first_name //todo
            binding.secondaryText.text = profile.city //todo


            galleryPosition = 0
            gallery = profile.photos
            if (gallery.isNullOrEmpty())
                gallery = listOf("https://img.myloview.fr/stickers/default-profile-picture-avatar-photo-placeholder-vector-illustration-400-197279432.jpg")
            //itemView.doOnLayout { binding.galleryIndicator.displayGalleryIndicators(gallery.size) } todo

            preloadPictures(profile.photos)
            displayPicture(gallery[0])

        }

        //------------------------------------- G L I D E ----------------------------------------//

        private fun displayPicture(url: String) {

            itemView.doOnLayout { // Make sur itemView is laid out to get width & height
                //binding.galleryIndicator.selectGalleryIndicator(galleryPosition) todo

                Glide
                    .with(binding.imageView.context)
                    .load(url)
                    //  Use custom target to avoid image blink
                    .into<CustomTarget<Drawable>>(
                        object : CustomTarget<Drawable>(itemView.width,  itemView.height) {
                            override fun onResourceReady(
                                drawable: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                binding.imageView.setImageDrawable(drawable)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                // Clear any ref to this drawable
                            }
                        })
            }
        }



        private fun preloadPictures(picUrls: List<String>) {
            for (url in picUrls) {
                Glide
                    .with(itemView.context)
                    .load(url)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .submit()
            }
        }
    }

}