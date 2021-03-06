package com.articrew.artic.ui.article_about

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.articrew.artic.R
import com.articrew.artic.data.Article
import com.articrew.artic.ui.article_webview.ArticleWebViewActivity
import com.articrew.artic.util.defaultHolderOptions
import com.articrew.artic.util.dpToPx
import com.articrew.artic.util.setTopRound
import com.bumptech.glide.Glide
import org.jetbrains.anko.startActivity

class ArticleAdapter(
    private val context: Context,
    var data: List<Article>
): RecyclerView.Adapter<ArticleAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item_article_about_article, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.run {
//            holder.img?.clipToOutline = true

            val cur = data[position]
            img?.let {
                Glide.with(context)
                    .load(cur.title_img_url)
                    .apply(defaultHolderOptions)
                    .into(it)
            }
            txt_title?.text = cur.title
            txt_url?.text = cur.domain_url
            container?.setOnClickListener {
                context.startActivity<ArticleWebViewActivity>("articleId" to cur.id)
            }
        }
    }

    inner class Holder(view: View): RecyclerView.ViewHolder(view) {
        val container = view.findViewById<View?>(R.id.container_rv_item_article_about_article)
        val img = view.findViewById<ImageView?>(R.id.img_rv_item_article_about_article)?.apply {
            setTopRound(6.dpToPx().toFloat())
        }
        val txt_title = view.findViewById<TextView?>(R.id.txt_rv_item_article_about_article_title)
        val txt_url = view.findViewById<TextView?>(R.id.txt_rv_item_article_about_article_url)
    }
}