package com.klima7.services.common.components.msgviewer

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.Gravity
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementRatingMessageBinding
import com.klima7.services.common.models.RatingMessage
import com.klima7.services.common.ui.convertDpToPixel
import com.xwray.groupie.databinding.BindableItem


class RatingMessageItem(
    private val message: RatingMessage,
    private val side: Side,
    private val listener: Listener,
    ) : BindableItem<ElementRatingMessageBinding>() {

    override fun bind(binding: ElementRatingMessageBinding, position: Int) {
        binding.apply {
            rating = String.format("%.2f", message.rating)
            binding.offerRating.rating = message.rating.toFloat()
            binding.msgratingShowButton.setOnClickListener {
                listener.showRatingClicked(message.ratingId)
            }

            if(side == Side.LEFT) {
                msgtextContainer.gravity = Gravity.LEFT
                msgtextContainer.setPadding(0, 0, convertDpToPixel(64f, msgtextContainer.context), 0)
                binding.cardColor = binding.msgtextCard.context.resources.getColor(R.color.left_message_cloud_color, null)
            }
            else if(side == Side.RIGHT) {
                msgtextContainer.gravity = Gravity.RIGHT
                msgtextContainer.setPadding(convertDpToPixel(64f, msgtextContainer.context), 0, 0, 0)
                binding.cardColor = getRightColor(binding.msgtextCard.context)
            }
        }
    }

    private fun getRightColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.messageCloudColor))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    override fun getLayout(): Int {
        return R.layout.element_rating_message
    }

    interface Listener {
        fun showRatingClicked(ratingId: String)
    }

}