package com.example.news_demo.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.news_demo.data.db.ArticleMapper
import com.example.news_demo.databinding.ItemArticleBinding

class NewsAdapter(
    private val context: Context,
    private val onClick: (ArticleMapper) -> Unit
) : PagingDataAdapter<ArticleMapper, NewsAdapter.NewsViewHolder>(diff) {

    inner class NewsViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticleMapper) {

            binding.title.text = article.title
            binding.description.text = article.description
            binding.source.text = "- source: ${article.sourceName}"

            Glide.with(context)
                .load(article.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.articleImage)

            binding.root.setOnClickListener {

                onClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val article = getItem(position)
        article?.let { holder.bind(it) }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<ArticleMapper>() {
            override fun areItemsTheSame(oldItem: ArticleMapper, newItem: ArticleMapper): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(
                oldItem: ArticleMapper,
                newItem: ArticleMapper
            ) = oldItem == newItem
        }
    }
}