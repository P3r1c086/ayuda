package com.pedroaguilar.ayuda

import com.pedroaguilar.ayuda.entities.Article

/**
 * Esta clase sirve para poder comunicar entre la clase MainActivity y la clase Adapter, encargada de manejar estos listados.
 */
interface OnclickListener {
    /**
     * Evento ordinario de check
     */
    fun onChecked(article: Article)
    /**
     * metodo para eliminar una nota
     */
    fun onLongClick(article: Article, currentAdapter: ArticleAdapter)
}