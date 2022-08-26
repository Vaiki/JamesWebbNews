package com.vaiki.jameswebbnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.databinding.FragmentArticleBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<NewsViewModel>()
   // val args: ArticleFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)
       val url = requireArguments().getString(ARTICLE_KEY)
      //  val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(url!!)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        const val ARTICLE_KEY = "article"
    }
}