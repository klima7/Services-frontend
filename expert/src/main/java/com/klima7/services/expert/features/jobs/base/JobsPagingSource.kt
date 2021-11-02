package com.klima7.services.expert.features.jobs.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.models.ExpertJob

class JobsPagingSource(
    private val jobsIds: List<String>,
    private val getCurrentExpertJobsUC: GetCurrentExpertJobsUC,
): PagingSource<Int, ExpertJob>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExpertJob> {
        try {
            val start = params.key ?: 0
            val startFixed = if (start <= jobsIds.size) start else jobsIds.size
            val end = start + params.loadSize
            val endFixed = if (end <= jobsIds.size) end else jobsIds.size
            val next = if(endFixed == jobsIds.size) null else endFixed+1

            val result = getCurrentExpertJobsUC.run(
                GetCurrentExpertJobsUC.Params(
                    jobsIds.subList(startFixed, endFixed)
                )
            )
                .getResultOrNull() ?: return LoadResult.Error(Error("getJobsUC failure"))
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = next
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ExpertJob>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
        }
    }

}