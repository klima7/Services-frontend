package com.klima7.services.expert.features.services

import com.klima7.services.common.models.Service

data class SelectableService(var service: Service, var selected: Boolean = false)