package com.klima7.services.client.features.jobs

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.models.Job

class JobsPagingSource(
    private val getCurrentClientJobsUC: GetCurrentClientJobsUC
): PagingSource<String, Job>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Job> {
        try {
            val result = getCurrentClientJobsUC.run(
                GetCurrentClientJobsUC.Params(params.key, params.loadSize)
            ).getResultOrNull() ?: return LoadResult.Error(Error("getCurrentClientJobsUC failure"))
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if(result.size == params.loadSize) result.last().id else null
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Job>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
        }
    }

}