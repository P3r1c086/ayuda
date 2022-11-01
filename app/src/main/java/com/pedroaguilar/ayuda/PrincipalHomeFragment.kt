package com.pedroaguilar.ayuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.pedroaguilar.ayuda.databinding.FragmentLoginBinding
import com.pedroaguilar.ayuda.databinding.FragmentPrincipalHomeBinding

class PrincipalHomeFragment : Fragment() {
    private lateinit var binding: FragmentPrincipalHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPrincipalHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners(){
        binding.tvDonar.setOnClickListener {
                v -> Navigation.findNavController(v).navigate(R.id.action_principalHomeFragment_to_categoriasDonarFragment)
        }
        binding.tvBuscar.setOnClickListener {
                v -> Navigation.findNavController(v).navigate(R.id.action_principalHomeFragment_to_busquedasFragment)
        }
    }
}