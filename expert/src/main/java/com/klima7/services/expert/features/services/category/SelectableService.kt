package com.klima7.services.expert.features.services.category

import com.klima7.services.common.domain.models.Service

data class SelectableService(var service: Service, var selected: Boolean = false)