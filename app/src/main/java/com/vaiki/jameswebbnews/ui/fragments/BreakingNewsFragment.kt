package com.vaiki.jameswebbnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.databinding.FragmentBreakingNewsBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<NewsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBreakingNewsBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}