package com.pedroaguilar.ayuda

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentBusquedasBinding
import com.pedroaguilar.ayuda.entities.Article
import com.pedroaguilar.ayuda.entities.Category
import java.util.*


class BusquedasFragment : Fragment(), OnclickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentBusquedasBinding
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var database: DatabaseHelper
    private lateinit var articleFavoriteAdapter: ArticleAdapter
    private lateinit var arrayAdapterCategories: ArrayAdapter<String>
    private lateinit var listArticle: MutableList<Article>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusquedasBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        arrayAdapterCategories =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        arrayAdapterCategories.addAll(
            Arrays.asList(
                "Categorias",
                "Comida",
                "Muebles",
                "Ropa",
                "Electrodomésticos",
                "Voluntariado",
                "Bebes"
            )
        )
        binding.spinnerCategories.onItemSelectedListener = this
        binding.spinnerCategories.adapter = arrayAdapterCategories

//        binding.etBuscar.setOnFocusChangeListener { view, b ->
//
//            if (binding.etBuscar.text.toString().isEmpty()) {
//                listArticle = database.getAllArticles()
//            }
//        }
        //agregar nota en tiempo real
        //evento de click para el boton agregar nueva nota
        binding.btnSearch.setOnClickListener {
            //comprobar si no esta en blanco
            if (binding.etBuscar.text.toString().isNotBlank()) {
                //la descripcion la sacamos de lo que introduzca el usuario en el EditText
                val article = Article(nameArticle = binding.etBuscar.text.toString().trim())
                for (i in 0..listArticle.size - 1) {
                    if (!listArticle.get(i).nameArticle.contains(article.nameArticle)) {
                        articleAdapter.remove(listArticle.get(i))
                    } else if (listArticle.isEmpty()) {
                        Toast.makeText(
                            context,
                            getString(R.string.error_upload_article),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                //si esta vacio
                binding.etBuscar.error = getString(R.string.validation_field_required)
            }
        }
    }

    private fun showMessage(msgRes: Int) {
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_SHORT).show()
    }

    private fun addArticleAuto(article: Article) {
        //si la nota esta terminada
        if (article.isFavorite) {
            //le paso esa nota al adaptador de notas terminadas
            articleFavoriteAdapter.add(article)
        }
//        else{//si no esta finalizada se la paso al adaptador con las notas pendientes
//            articleAdapter.add(article)
//        }

    }

    private fun deleteArticleAuto(article: Article) {
        if (article.isFavorite) {
            articleAdapter.remove(article)
        } else {
            articleFavoriteAdapter.remove(article)
        }

    }

    override fun onChecked(article: Article) {
        //antes de mover las notas debo actualizar el campo isChecked en la bd
        //con esto compruebo si la actualizacion ha tenido exito
        if (database.updateArticle(article)) {
            //para poder mover las notas de un listado a otro
            // primero elimino la nota de donde se encuentra actualmente y
//            deleteArticleAuto(article)
            // luego la agrego en el contrario
            addArticleAuto(article)
        } else {//si no se ha actualizado correctamente
            showMessage(R.string.message_write_database_error)
        }
    }

    override fun onLongClick(article: Article, currentAdapter: ArticleAdapter) {
        //crear un alertDialog para prevenir al usuario de que la nota se va a borrar
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                //primero borramos la nota de la bd y comprobamos si fue exitoso el borrado
                if (database.deleteArticle(article)) {
                    //borramos la nota del adaptador el cual a la vez modifica la vista del usuario
                    currentAdapter.remove(article)
                    showMessage(R.string.message_delete_database_success)
                } else {
                    showMessage(R.string.message_delete_database_error)
                }
            })
            .setNegativeButton(getString(R.string.dialog_cancel), null)
        builder.create().show()

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        val categorySelected = arrayAdapterCategories.getItem(position)
        if (position != 0) {
            //todo: Cuando meto un nuevo articulo en otra categoria se borra el campo categoria del otro articulo
            database = DatabaseHelper(requireContext())
            listArticle = database.getAllArticles()
            articleAdapter = ArticleAdapter(mutableListOf(), this)
            articleFavoriteAdapter = ArticleAdapter(mutableListOf(), this)
            val listArticle = database.getArticlesForCategory(categorySelected.toString().trim())
            articleAdapter.articleList.clear()
            articleAdapter.notifyDataSetChanged()

            listArticle.forEach {
                if (it.categoryArticle.equals(categorySelected.toString().trim())) {
                    articleAdapter.add(it)
                }

            }

            binding.rvArticulos.apply {
                layoutManager = LinearLayoutManager(requireContext()) //this@BusquedasFragment...........................
                adapter = articleAdapter

            }
        } else {
            database = DatabaseHelper(requireContext())
            listArticle = database.getAllArticles()
            articleAdapter = ArticleAdapter(mutableListOf(), this)
            articleFavoriteAdapter = ArticleAdapter(mutableListOf(), this)

            listArticle.forEach {
                articleAdapter.add(it)
            }

            binding.rvArticulos.apply {
                layoutManager = LinearLayoutManager(requireContext()) //this@BusquedasFragment...........................
                adapter = articleAdapter
            }
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}