package com.vaiki.jameswebbnews.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.adapters.NewsAdapter
import com.vaiki.jameswebbnews.databinding.FragmentSearchNewsBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import com.vaiki.jameswebbnews.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter
    private val viewModel by sharedViewModel<NewsViewModel>()
    val TAG = "Search Fragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchNewsBinding.bind(view)
        initRecyclerView()
        newsAdapter.setOnItemClickListener {
            viewModel.openArticle(it)
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment
            )
        }
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchNews(it.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, message)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }


    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}