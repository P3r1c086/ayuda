package com.pedroaguilar.ayuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentBusquedasBinding
import com.pedroaguilar.ayuda.databinding.FragmentFavoritesBinding
import com.pedroaguilar.ayuda.entities.Article

class FavoritesFragment : Fragment(R.layout.fragment_favorites), OnclickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var database: DatabaseHelper
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleFavoriteAdapter: ArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Adaptador para las articulos favoritos
         */
        //vincular la vista .xml donde esta el recyclerView con el adaptador
        //inicializamos el adaptador, al que le pasamos una lista vacia que podra ser inicializada
        // y un listener tomo el listener de la interfaz que esta heredando esta clase
        database = DatabaseHelper(requireContext())
        var listArticleFav = database.getArticlesFavorites()
        articleFavoriteAdapter = ArticleAdapter(mutableListOf(), this)

        if (!listArticleFav.isEmpty()){
            listArticleFav.forEach{
                articleFavoriteAdapter.add(it)
            }
        }else{
            binding.tvInfo.text = getString(R.string.title_info_no_fav)
        }


        //vinculamos el adaptador con el recyclerView
        binding.rvArticulosFavoritos.apply {
            //ponemos como contexto el de la actividad, no el del recyclerView
            layoutManager = LinearLayoutManager(requireContext())//this@FavoritesFragment................
            adapter = articleFavoriteAdapter
        }
    }

    private fun addArticleAuto(article: Article) {
        //si la nota esta terminada
        if (article.isFavorite){
            //le paso esa nota al adaptador de notas terminadas
            articleFavoriteAdapter.add(article)
        }
//        else{//si no esta finalizada se la paso al adaptador con las notas pendientes
//            articleAdapter.add(article)
//        }
    }

    private fun deleteArticleAuto(article: Article) {
        if (article.isFavorite){
            //articleFavoriteAdapter.remove(article)
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_title))
                .setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                    //primero borramos la nota de la bd y comprobamos si fue exitoso el borrado
                    if (database.deleteArticle(article)) {
                        //borramos la nota del adaptador el cual a la vez modifica la vista del usuario
                        articleFavoriteAdapter.remove(article)
                        showMessage(R.string.message_delete_database_success)
                    } else {
                        showMessage(R.string.message_delete_database_error)
                    }
                })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
            builder.create().show()
        }


    }

    override fun onChecked(article: Article) {
        //antes de mover las notas debo actualizar el campo isChecked en la bd
        //con esto compruebo si la actualizacion ha tenido exito
        if (database.updateArticle(article)){
            //para poder mover las notas de un listado a otro
            // primero elimino la nota de donde se encuentra actualmente y
            deleteArticleAuto(article)
            // luego la agrego en el contrario
            addArticleAuto(article)
        }else{//si no se ha actualizado correctamente
            showMessage(R.string.message_write_database_error)
        }
    }

    override fun onLongClick(article: Article, currentAdapter: ArticleAdapter) {
        //crear un alertDialog para prevenir al usuario de que la nota se va a borrar
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                //primero borramos la nota de la bd y comprobamos si fue exitoso el borrado
                if (database.deleteArticle(article)){
                    //borramos la nota del adaptador el cual a la vez modifica la vista del usuario
                    currentAdapter.remove(article)
                    showMessage(R.string.message_delete_database_success)
                }else{
                    showMessage(R.string.message_write_database_error)
                }
            })
            .setNegativeButton(getString(R.string.dialog_cancel), null)
        builder.create().show()
    }

    private fun showMessage(msgRes: Int){
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }
}