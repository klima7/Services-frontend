package com.klima7.services.client.features.newjob.newjob

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Service
import com.klima7.services.common.platform.BaseViewModel

class NewJobViewModel: BaseViewModel() {

    val subtitle = MutableLiveData<String>()
    val position = MutableLiveData(1)

    val category = MutableLiveData<Category>()
    val service = MutableLiveData<Service>()

}