package com.pedroaguilar.ayuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentUploadBinding
import com.pedroaguilar.ayuda.entities.Article
import com.pedroaguilar.ayuda.entities.User

class UploadFragment : Fragment(R.layout.fragment_upload) {

    private lateinit var binding: FragmentUploadBinding
    private lateinit var database: DatabaseHelper
    private lateinit var article: Article

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null){
            article = Article(categoryArticle = requireArguments().getString("category").toString())
        }

        database = DatabaseHelper(requireContext())
        setListener()
    }

    private fun setListener(){
        binding.btnAdd.setOnClickListener {
            if (binding.etTitleArticle.text.toString().isNotBlank() &&
                binding.etDescriptionArticle.text.toString().isNotBlank()){
               // val article = Article()
                article.nameArticle = binding.etTitleArticle.text.toString()
                article.description = binding.etDescriptionArticle.text.toString()

                database.insertArticle(article)
                Toast.makeText(context,"Articulo subido correctamente.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, getString(R.string.validation_field_required), Toast.LENGTH_SHORT).show()
            }
        }
    }
}