package com.vaiki.jameswebbnews.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.databinding.FragmentSavedNewsBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<NewsViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedNewsBinding.bind(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}