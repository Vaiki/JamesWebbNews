package com.vaiki.jameswebbnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.databinding.ItemArticlePreviewBinding
import com.vaiki.jameswebbnews.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(article) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener:((Article)->Unit)? = null
    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener = listener
    }

}

class NewsViewHolder(private val binding: ItemArticlePreviewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        with(binding) {
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            Glide.with(itemView.context)
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_newspaper)
                .into(ivArticleImage)

        }
    }

}
