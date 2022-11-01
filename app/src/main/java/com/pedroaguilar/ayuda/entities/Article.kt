package com.pedroaguilar.ayuda.entities

data class Article(var id: Long = 0,
                   var nameArticle: String = "",
                   var categoryArticle: String = "",
                   var description: String = "",
                   var isFavorite: Boolean = false)
