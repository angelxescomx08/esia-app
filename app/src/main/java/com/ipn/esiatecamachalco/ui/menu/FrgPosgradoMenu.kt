package com.ipn.esiatecamachalco.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.ViewModelMain
import com.ipn.esiatecamachalco.databinding.LayoutFrgMenuPosgradoBinding

class FrgPosgradoMenu: Fragment() {

    private lateinit var binding: LayoutFrgMenuPosgradoBinding
    private val vmm: ViewModelMain by activityViewModels()

    private val TAG: String = this::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgMenuPosgradoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)

        binding.txtConocenos.setOnClickListener {
            controller.navigate(FrgPosgradoMenuDirections.actionFrgPosgradoMenuToFrgConocenosUno())
        }
        binding.txtProgramas.setOnClickListener {
            controller.navigate(FrgPosgradoMenuDirections.actionFrgPosgradoMenuToFrgProgramasUno())
        }
        binding.txtAlumnos.setOnClickListener {
            controller.navigate(FrgPosgradoMenuDirections.actionFrgPosgradoMenuToFrgAlumnosUno())
        }
        binding.txtProfesores.setOnClickListener {
            controller.navigate(FrgPosgradoMenuDirections.actionFrgPosgradoMenuToFrgProfesores())
        }
    }
}