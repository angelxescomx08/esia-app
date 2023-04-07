package com.ipn.esiatecamachalco.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.databinding.LayoutFrgUserTypeBinding

class FrgUserType: Fragment() {

    private lateinit var binding: LayoutFrgUserTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutFrgUserTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)

        binding.btnPosgrado.setOnClickListener {
            controller.navigate(FrgUserTypeDirections.actionFrgUserTypeToFrgPosgradoMenu())
        }
        binding.btnAlumnos.setOnClickListener {
            controller.navigate(FrgUserTypeDirections.actionFrgUserTypeToNavigationMenu())
        }
        binding.btnDocentes.setOnClickListener {
            controller.navigate(FrgUserTypeDirections.actionFrgUserTypeToNavigationMenu())
        }
    }
}
