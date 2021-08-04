package com.example.news_demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.news_demo.R
import com.example.news_demo.data.db.ArticleMapper
import com.example.news_demo.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsDetailBinding.bind(view)

        val args = arguments?.getParcelable<ArticleMapper>("article")

        args?.let {

            binding.title.text = it.title
            binding.description.text = it.description
            binding.author.text = "author: ${it.author}"
            binding.content.text = it.content
            binding.source.text = "(source: ${it.sourceName})"
            binding.time.text = "published at: ${it.publishedAt}"

            Glide.with(requireContext())
                .load(it.urlToImage)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.articleImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}