package com.ipn.esiatecamachalco.ui.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.library.alert.clases.KUtils
import com.ipn.esiatecamachalco.databinding.LayoutFrgTramitesEscolaresUnoBinding

class FrgTramitesEscolaresUno: Fragment() {

    private lateinit var binding: LayoutFrgTramitesEscolaresUnoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgTramitesEscolaresUnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasFormacionIntegralInstitucional.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/esiatectitulacion"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.txtVerMasFormacionBasica.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgTramitesEscolaresUnoDirections.actionTramitesDos())
        }
        binding.fabNext.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgTramitesEscolaresUnoDirections.actionTramitesDos())
        }
    }
}