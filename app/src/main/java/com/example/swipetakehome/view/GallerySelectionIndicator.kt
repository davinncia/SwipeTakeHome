package com.example.swipetakehome.view

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import com.example.swipetakehome.R

class GallerySelectionIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    fun displayGalleryIndicators(pictureCount: Int) {
        val marginPx = (MARGIN_DP * Resources.getSystem().displayMetrics.density).toInt()
        val width = (this.width - marginPx * pictureCount * 2) / pictureCount

        val indicatorParams = LayoutParams(width, LayoutParams.MATCH_PARENT)
        indicatorParams.setMargins(marginPx, 0, marginPx, 0)

        for (i in 0 until pictureCount) {
            this.apply {
                val indicator = ImageView(context).apply {
                    maxWidth = width
                    setImageResource(R.drawable.gallery_indicator)
                    layoutParams = indicatorParams
                    tag = i
                }
                addView(indicator)
            }
        }

        selectGalleryIndicator(0)
    }

     fun selectGalleryIndicator(position: Int) {
        this.allViews.forEach { view ->
            if (view !is ImageView) return@forEach

            val colorResId = if (view.tag == position) R.color.white else R.color.indicator_unselected
            setFillColor(view, colorResId)
        }
    }

    private fun setFillColor(view: View, colorResId: Int) {
        if (view !is ImageView) return

        (view.drawable as? GradientDrawable)?.setColor(
            ResourcesCompat.getColor(resources, colorResId, null)
        )
    }

    companion object {
        private const val MARGIN_DP = 2
    }

}