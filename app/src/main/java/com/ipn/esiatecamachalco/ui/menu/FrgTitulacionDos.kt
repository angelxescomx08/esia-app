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
import com.ipn.esiatecamachalco.databinding.LayoutFrgServicioSocialDosBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgServicioSocialUnoBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgTitulacionDosBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgTitulacionUnoBinding

class FrgTitulacionDos: Fragment() {

    private lateinit var binding: LayoutFrgTitulacionDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgTitulacionDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasExperienciaProf.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1iTeaFVAeLBLU5wARmk3xZSvrtD5U0fjc/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasSeminarios.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1UfjGnsJfGj_FXRC2R280J-ivHpJ--E1j/view?usp=share_link"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasSelect.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://select-esiatec.com.mx/"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}