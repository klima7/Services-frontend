package com.klima7.services.expert.features.offers.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.models.Offer

class OffersPagingSource(
    private val getOffersForCurrentExpert: GetOffersForCurrentExpertUC,
): PagingSource<String, Offer>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Offer> {
        try {
            val result = getOffersForCurrentExpert.run(
                GetOffersForCurrentExpertUC.Params(params.key, params.loadSize)
            ).getResultOrNull() ?: return LoadResult.Error(Error("getOffersForCurrentExpert failure"))
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if(result.size == params.loadSize) result.last().id else null
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Offer>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
        }
    }

}