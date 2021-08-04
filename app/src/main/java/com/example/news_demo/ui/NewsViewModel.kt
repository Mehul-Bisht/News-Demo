package com.example.news_demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news_demo.data.NewsRepository
import com.example.news_demo.data.db.ArticleMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

    private val _data: MutableStateFlow<PagingData<ArticleMapper>?> = MutableStateFlow(null)
    val data: StateFlow<PagingData<ArticleMapper>?> get() = _data

    init {

        viewModelScope.launch {

            getData().collectLatest {

                _data.value = it
            }
        }
    }

    @ExperimentalPagingApi
    private fun getData(): Flow<PagingData<ArticleMapper>> {

        return repository.getHeadLines().cachedIn(viewModelScope)
    }
}