package com.ipn.esiatecamachalco.ui.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.ViewModelMain
import com.ipn.esiatecamachalco.library.alert.clases.TypeAlert
import com.ipn.esiatecamachalco.databinding.LayoutFrgMenuBinding
import com.ipn.esiatecamachalco.library.alert.CustomAlert

class FrgMenu: Fragment() {

    private lateinit var binding: LayoutFrgMenuBinding
    private val vmm: ViewModelMain by activityViewModels()

    private val TAG: String = this::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtSAES.setOnClickListener {
            CustomAlert(requireContext()).apply {
                setData(
                    getString(R.string.app_name),
                    "¿Desea abrir la página del SAES?",
                    "Si","No"
                )
                setOnPositive {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.saes.esiatec.ipn.mx/"))
                    try {
                        startActivity(browserIntent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                        Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
                    }
                }
                show(TypeAlert.Question)
            }
        }

        binding.txtBecas.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionBecas())
        }

        binding.txtTramites.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionTramites())
        }

        binding.txtPosgrado.setOnClickListener {
            vmm.showPosgrado = true
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionMenuPosgradoMenu())
        }

        binding.txtServicioSocial.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionServicioSocialUno())
        }

        binding.txtPracticas.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionNavigationMenuToFrgPracticasUno())
        }

        binding.txtTitulacion.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionNavigationMenuToFrgTitulacionUno())
        }

        binding.txtApoyo.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgMenuDirections.actionNavigationMenuToFrgApoyoEstudiantilUno())
        }

        binding.txtPlanos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1__ZprHFRzmTOc9ggsdHjdVUVvhXnWHdc/view?usp=share_link"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}