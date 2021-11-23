package com.klima7.services.common.ui

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.willy.ratingbar.BaseRatingBar

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = BaseRatingBar::class,
            attribute = "srb_rating",
            method = "setRating"
        ),
    ]
)
class RatingBarBindingMethods

@InverseBindingAdapter(attribute = "srb_rating")
fun getRatingValue(ratingBar: BaseRatingBar) = ratingBar.rating

@BindingAdapter( "srb_ratingAttrChanged")
fun setRatingListeners(ratingBar: BaseRatingBar, attrChange: InverseBindingListener) {
    ratingBar.setOnRatingChangeListener { _, _, _ ->
        attrChange.onChange()
    }
}
