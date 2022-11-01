package com.pedroaguilar.ayuda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.pedroaguilar.ayuda.activities.PanelPrincipalActivity
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var db: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DatabaseHelper(requireContext())
        setListeners()
    }

    private fun setListeners(){
        binding.tvIrRegistro.setOnClickListener { v -> findNavController(v).navigate(R.id.action_loginFragment_to_newUserFragment)}
        binding.btEntrar.setOnClickListener {

            if (binding.etEmail.text.toString().isEmpty() || binding.etPassword.text.toString().isEmpty()){
                Toast.makeText(getContext(), "Campo vacio", Toast.LENGTH_SHORT).show()
            }else{
                val users = db.getAllUsers()
                users.forEach {
                    if (it.email.equals(binding.etEmail.text.toString()) && it.password.equals(binding.etPassword.text.toString())){
                        //Navegamos a la nueva actividad y matamos esta para que no exista navegacion a ella de nuevo. Aquí cambiamos de grafo de navegacion.

                        ///////////guardar el id del usuario que coincide aqui...it....y pasarselo a la clase DatabaseHelper

                        startActivity(Intent(requireContext(), PanelPrincipalActivity::class.java))
                        requireActivity().finish()

                    }else if (binding.etEmail.text.toString() != it.email){
                        Toast.makeText(getContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
    }
}