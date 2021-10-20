package com.klima7.services.common.ui.profile.rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.ui.base.BaseViewModel

class ProfileRatingViewModel: BaseViewModel() {

    private val expert = MutableLiveData<Expert>()
    val rating = expert.map { it.rating.toFloat() }
    val ratingText = expert.map { String.format("%.2f", it.rating) }
    val countText = expert.map {it.ratingsCount.toString() }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}