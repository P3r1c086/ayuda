package com.pedroaguilar.ayuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.pedroaguilar.ayuda.databinding.FragmentCategoriasDonarBinding


class CategoriasDonarFragment : Fragment(R.layout.fragment_categorias_donar) {

    private lateinit var binding: FragmentCategoriasDonarBinding
    private lateinit var bundle: Bundle

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriasDonarBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }

    private fun setListener(){
        binding.tvComida.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvComida.text.toString())
                    Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
        binding.tvMuebles.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvMuebles.text.toString())
            Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
        binding.tvRopa.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvRopa.text.toString())
            Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
        binding.tvElectrodomesticos.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvElectrodomesticos.text.toString())
            Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
        binding.tvVoluntariado.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvVoluntariado.text.toString())
            Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
        binding.tvBebes.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("category", binding.tvBebes.text.toString())
            Navigation.findNavController(v).navigate(R.id.action_categoriasDonarFragment_to_uploadFragment, bundle)
        }
    }
}