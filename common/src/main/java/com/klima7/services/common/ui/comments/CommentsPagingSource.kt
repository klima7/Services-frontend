package com.klima7.services.common.ui.comments

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.klima7.services.common.ui.rating.RatingWithProfileImage

class CommentsPagingSource(
    private val getExpertRatingsWithProfileImagesUC: GetExpertRatingsWithProfileImagesUC,
    private val expertId: String,
): PagingSource<String, RatingWithProfileImage>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RatingWithProfileImage> {
        try {
            val result = getExpertRatingsWithProfileImagesUC.run(
                GetExpertRatingsWithProfileImagesUC.Params(
                    expertId, params.key, params.loadSize
                )
            )
                .getResultOrNull() ?: return LoadResult.Error(Error("getRatingsForExpert failure"))
            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = result.last().rating.id
            )
        } catch(e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, RatingWithProfileImage>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey
        }
    }

}