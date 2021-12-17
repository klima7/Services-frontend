package com.klima7.services.common.ui

import android.content.Context
import com.klima7.services.common.R
import com.klima7.services.common.models.OfferStatus

data class OfferStatusDescription(
    val textId: Int, val imageId: Int
) {

    companion object {

        private val all = mapOf(
            OfferStatus.NEW to OfferStatusDescription(R.string.offer_status__new, 0),
            OfferStatus.CANCELLED to OfferStatusDescription(R.string.offer_status__cancelled, 0),
            OfferStatus.IN_REALIZATION to OfferStatusDescription(R.string.offer_status__in_realization, 0),
            OfferStatus.DONE to OfferStatusDescription(R.string.offer_status__done, 0),
        )

        fun get(offerStatus: OfferStatus): OfferStatusDescription {
            return all[offerStatus] ?:
            throw Exception("No offer status description found for: $offerStatus")
        }
    }

    fun getText(context: Context): String {
        return context.resources.getString(textId)
    }

}