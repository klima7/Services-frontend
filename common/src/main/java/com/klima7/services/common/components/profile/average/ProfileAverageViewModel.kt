package com.klima7.services.common.components.profile.average

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseViewModel

class ProfileAverageViewModel: BaseViewModel() {

    private val expert = MutableLiveData<Expert>()
    val rating = expert.map { it.rating.toFloat() }
    val ratingText = expert.map { String.format("%.2f", it.rating) }
    val countText = expert.map {it.ratingsCount.toString() }

    fun setExpert(expert: Expert) {
        this.expert.value = expert
    }

}