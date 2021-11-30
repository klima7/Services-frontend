package com.klima7.services.common.ui

import android.content.Context
import com.klima7.services.common.R
import com.klima7.services.common.models.OfferStatus

object OfferStatusStringifier {

    fun stringify(context: Context, status: OfferStatus): String {
        val resourceId = when(status) {
            OfferStatus.NEW -> R.string.offer_status_new
            OfferStatus.CANCELLED -> R.string.offer_status_rejected
            OfferStatus.IN_REALIZATION -> R.string.offer_status_in_realization
            OfferStatus.DONE -> R.string.offer_status_done
        }
        return context.resources.getString(resourceId)
    }

}