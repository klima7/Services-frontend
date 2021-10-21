//package com.klima7.services.common.ui.comments
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.klima7.services.common.data.repositories.RatingsRepository
//import org.w3c.dom.Comment
//import java.lang.Exception
//
//class CommentsPagingSource(
//    private val ratingsRepository: RatingsRepository,
//    private val expertId: String,
//): PagingSource<String, Comment>() {
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, Comment> {
//        try {
//            val nextPageNr = params.key
//            val result = ratingsRepository.getRatingsForExpert()
//        } catch(e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<String, Comment>): String? {
//        TODO("Not yet implemented")
//    }
//
//}