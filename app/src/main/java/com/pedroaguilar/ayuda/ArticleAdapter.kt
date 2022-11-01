package com.pedroaguilar.ayuda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.pedroaguilar.ayuda.databinding.ItemArticleBinding
import com.pedroaguilar.ayuda.entities.Article

class ArticleAdapter(var articleList: MutableList<Article>,private val listener: OnclickListener) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList.get(position)
        holder.setListener(article)
        holder.binding.tvTitleCard.text = article.nameArticle
        holder.binding.tvDescription.text = article.description
        holder.binding.cbFavorite.isChecked = article.isFavorite
    }

    override fun getItemCount(): Int = articleList.size

    fun add(article: Article) {
        articleList.add(article)
        //despues de agregar la nota tenemos que notificarle al adaptador que refresque el listado y por tanto la lista
        notifyDataSetChanged()
    }

    fun remove(article: Article) {
        articleList.remove(article)
        //despues de borrar la nota tenemos que notificarle al adaptador que refresque el listado y por tanto la lista
        notifyDataSetChanged()
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val binding = ItemArticleBinding.bind(view)

        fun setListener(article: Article) {
            binding.cbFavorite.setOnClickListener {
                article.isFavorite = (it as CheckBox).isChecked
                listener.onChecked(article)
            }
            binding.root.setOnLongClickListener{
                listener.onLongClick(article, this@ArticleAdapter)
                true
            }
        }
    }


}