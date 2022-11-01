package com.pedroaguilar.ayuda

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pedroaguilar.ayuda.database.DatabaseHelper
import com.pedroaguilar.ayuda.databinding.FragmentSettingsBinding
import com.pedroaguilar.ayuda.entities.User
import kotlinx.coroutines.currentCoroutineContext


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteUser()
        signOut()
    }

    private fun deleteUser(){
        user = User()
        databaseHelper = DatabaseHelper(requireContext())
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("¿Borrar usuario?")
            .setPositiveButton("Borrar", {dialogInterface, i ->
                databaseHelper.deleteUser(user)
                Toast.makeText(requireContext(), getString(R.string.text_delete_user_ok), Toast.LENGTH_SHORT).show()
                val mainActivityIntent = Intent(requireContext(), SettingsFragment::class.java)
                startActivity(mainActivityIntent)
            })
            .setNegativeButton("Cancelar", null)
        builder.create().show()
    }

    private fun signOut(){
        binding.tvSignOut.setOnClickListener {
            //todo: desloguear al usuario que esta usando la app
            val mainActivityIntent = Intent(requireContext(), SettingsFragment::class.java)
            startActivity(mainActivityIntent)
        }
    }
}