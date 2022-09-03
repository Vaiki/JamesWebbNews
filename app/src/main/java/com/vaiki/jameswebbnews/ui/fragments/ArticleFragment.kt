package com.vaiki.jameswebbnews.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.databinding.FragmentArticleBinding
import com.vaiki.jameswebbnews.ui.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<NewsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)

        viewModel.openArticleLiveData.observe(viewLifecycleOwner) { article ->
            binding.webView.apply {
                webViewClient = WebViewClient()
                article.url?.let { loadUrl(it) }
            }
            binding.fabSave.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar.make(view,"Article saved successfully",Snackbar.LENGTH_SHORT).show()
            }
            binding.fabShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, article.url)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
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