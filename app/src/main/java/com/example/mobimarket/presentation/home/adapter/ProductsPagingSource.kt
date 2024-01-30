package com.example.mobimarket.presentation.home.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.source.remote.MobiApi
import com.example.mobimarket.data.source.remote.MobiRepositoryImpl
import javax.inject.Inject

class ProductsPagingSource @Inject constructor(
    private val limit: Int,
    private val accessToken: String,
    mobiApi: MobiApi
) : PagingSource<Int, Product>(){
    private val repository = MobiRepositoryImpl(mobiApi)

    override fun getRefreshKey(state: PagingState<Int, Product>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try{ // or you can use kotlin.runCatching
            val response = repository.getProducts(pageIndex,limit,accessToken)
           LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if(response.isEmpty()) null else pageIndex+1
            )
        }catch (e : Exception){
            LoadResult.Error(e)
        }
    }
    private companion object {
        private const val FIRST_PAGE = 1
        private const val STARTING_PAGE_INDEX = 1
    }
}
