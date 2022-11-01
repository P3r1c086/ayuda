package com.pedroaguilar.ayuda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentNewUserBinding
import com.pedroaguilar.ayuda.entities.User

class NewUserFragment : Fragment() {

    private lateinit var binding: FragmentNewUserBinding
    private lateinit var db: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentNewUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //instancio la base de datos
        db = DatabaseHelper(requireContext())
        setListeners()
    }

    private fun setListeners(){
        binding.btAceptar.setOnClickListener {
            if (activity != null){
                val user = User()
                user.apply {
                    name = binding.etNombreReal.text.toString()
                    surname = binding.etApellidos.text.toString()
                    email = binding.etEmail.text.toString()
                    password = binding.etPass.text.toString()
                    address = binding.etDireccion.text.toString()
                    phone = binding.etTelefono.text.toString()
                }
                db.insertUser(user)
                Toast.makeText(getContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(getContext(), "Error al crear el usuario", Toast.LENGTH_SHORT).show()
            }

        }
    }
}