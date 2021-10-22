package com.klima7.services.common.ui.comments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.domain.models.Rating

class CommentsPagingSource(
    private val getRatingsForExpertUC: GetRatingsForExpertUC,
    private val expertId: String,
): PagingSource<String, Rating>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Rating> {
        try {
            val result = getRatingsForExpertUC.run(
                GetRatingsForExpertUC.Params(
                    expertId, params.key, params.loadSize
                )
            )
                .getResultOrNull() ?: return LoadResult.Error(Error("getRatingsForExpertUC failure"))
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = result.last().id
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Rating>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
        }
    }

}