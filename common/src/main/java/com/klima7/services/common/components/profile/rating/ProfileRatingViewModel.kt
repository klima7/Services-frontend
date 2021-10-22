package com.klima7.services.common.components.profile.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.base.BaseViewModel

class ProfileRatingViewModel: BaseViewModel() {

    private val expert = MutableLiveData<Expert>()
    val rating = expert.map { it.rating.toFloat() }
    val ratingText = expert.map { String.format("%.2f", it.rating) }
    val countText = expert.map {it.ratingsCount.toString() }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}