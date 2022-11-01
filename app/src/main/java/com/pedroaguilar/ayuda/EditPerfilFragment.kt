package com.pedroaguilar.ayuda

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentEditPerfilBinding
import com.pedroaguilar.ayuda.entities.User

class EditPerfilFragment : Fragment() {
    private lateinit var binding: FragmentEditPerfilBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var user: User
    private var imgUri: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditPerfilBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        //user = User(id = databaseHelper.getUser().get(0) as Long)

        if (arguments != null){
            binding.etName.setText(requireArguments().getString("name"))
            binding.etSurname.setText(requireArguments().getString("surname"))
            binding.etAddress.setText(requireArguments().getString("address"))
            binding.etPhone.setText(requireArguments().getString("phone"))
        }
        setListener()

    }
    private fun setListener(){
        user = User()
        binding.btnEnterEdit.setOnClickListener {
            user.name = binding.etName.text.toString()
            user.surname = binding.etSurname.text.toString()
            user.address = binding.etAddress.text.toString()
            user.phone = binding.etPhone.text.toString()
            databaseHelper.updateUser(user)

        }
        binding.btnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/jpeg"
            }
            startActivityForResult(intent, RC_GALLERY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            if (requestCode == RC_GALLERY){
                imgUri = data?.data
                binding.imgProfile.setImageURI(imgUri)
            }
        }
    }
    companion object{
        private const val RC_GALLERY = 22
    }
}