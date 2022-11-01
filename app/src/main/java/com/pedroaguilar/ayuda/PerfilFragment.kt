package com.pedroaguilar.ayuda

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentLoginBinding
import com.pedroaguilar.ayuda.databinding.FragmentPerfilBinding
import com.pedroaguilar.ayuda.entities.User

class PerfilFragment : Fragment(R.layout.fragment_perfil) {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var db: DatabaseHelper
    private lateinit var user: User
    private lateinit var bundle: Bundle
    private lateinit var editPerfilFragment: EditPerfilFragment
    private var imgUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DatabaseHelper(requireContext())
        user = User()
        val dataUser = db.getUser()
        binding.tvName.text = dataUser.get(1)
        binding.tvSurname.text = dataUser.get(2)
        binding.tvEmail.text = dataUser.get(3)
        binding.tvAddress.text = dataUser.get(4)
        binding.tvPhone.text = dataUser.get(5)
        setListeners()

    }

    private fun setListeners(){
        binding.btnIrEditar.setOnClickListener { v ->
            var bundle = Bundle()
            bundle.putString("name", binding.tvName.text.toString().trim())
            bundle.putString("surname", binding.tvSurname.text.toString().trim())
            bundle.putString("email", binding.tvEmail.text.toString().trim())
            bundle.putString("address", binding.tvAddress.text.toString().trim())
            bundle.putString("phone", binding.tvPhone.text.toString().trim())

            Navigation.findNavController(v).navigate(R.id.action_perfilFragment_to_editPerfilFragment, bundle)

        }
    }

}