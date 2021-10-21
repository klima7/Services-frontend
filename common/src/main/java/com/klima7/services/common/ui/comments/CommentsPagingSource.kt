package com.klima7.services.common.ui.comments

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.domain.models.Rating
import java.lang.Exception

class CommentsPagingSource(
    private val ratingsRepository: RatingsRepository,
    private val expertId: String,
): PagingSource<String, Rating>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Rating> {
        try {
            val result = ratingsRepository
                .getRatingsForExpert(expertId, params.key, params.loadSize)
                .getResultOrNull() ?: return LoadResult.Error(Error("getRatingsForExpert failure"))
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