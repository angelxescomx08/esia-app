package com.ipn.esiatecamachalco.ui.posgrado

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoConocenosDosBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoProgramasDosBinding

class FrgProgramasDos: Fragment() {

    private lateinit var binding: LayoutFrgPosgradoProgramasDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgPosgradoProgramasDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasEscolaridad.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sepi.esiatec.ipn.mx/oferta-educativa/mcra/inicio.html"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCreditoPosgrado.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sepi.esiatec.ipn.mx/oferta-educativa/especialidad-en-valuacion-inmobiliaria/inicio.html"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}