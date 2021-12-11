package com.klima7.services.common.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Rating(
    val id: String,
    val clientName: String,
    val serviceName: String,
    val comment: String?,
    val rating: Double,
    val date: Date,
    val expertId: String,
) : Parcelable
