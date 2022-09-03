package com.vaiki.jameswebbnews.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.adapters.NewsAdapter
import com.vaiki.jameswebbnews.databinding.FragmentSearchNewsBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import com.vaiki.jameswebbnews.util.Resource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*
import java.util.concurrent.TimeUnit

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter
    private val viewModel by sharedViewModel<NewsViewModel>()
    val TAG = "Search Fragment"
    @SuppressLint("CheckResult")
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
//        var job: Job? = null
//        binding.etSearch.addTextChangedListener {
//            job?.cancel()
//            job = MainScope().launch {
//                delay(500L)
//                it?.let {
//                    if (it.toString().isNotEmpty()) {
//                        viewModel.getSearchNews(it.toString())
//                    }
//                }
//            }
//        }
       Observable.create<String> { emitter ->
           binding.etSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
               override fun onQueryTextSubmit(query: String?): Boolean {
                  return false
               }

               override fun onQueryTextChange(newText: String?): Boolean {

                if (!emitter.isDisposed){
                    emitter.onNext(newText!!)
                }
                   return false
               }

           })
       }
           .debounce(1000,TimeUnit.MILLISECONDS)
           .subscribeOn(Schedulers.io())
           .filter { text -> text.isNotBlank() }
           .distinctUntilChanged()
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe { viewModel.getSearchNews(it)
           Log.e("rx", it)}

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
