package com.example.news_demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.news_demo.R
import com.example.news_demo.data.NewsAdapter
import com.example.news_demo.databinding.FragmentNewsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    @ExperimentalPagingApi
    private val viewModel by activityViewModels<NewsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNewsListBinding.bind(view)

        val navController = view.findNavController()

        val adapter = NewsAdapter(requireContext()) {

            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(it)
            navController.navigate(action)
        }

        binding.recyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {

            viewModel.data.collectLatest { data ->

                data?.let {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}