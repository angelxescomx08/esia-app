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
import com.ipn.esiatecamachalco.databinding.LayoutFrgTitulacionUnoBinding

class FrgTitulacionUno: Fragment() {

    private lateinit var binding: LayoutFrgTitulacionUnoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgTitulacionUnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasEscolaridad.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1ZEhUBHrL-8ZN1jHx3f2gLFcQu5kf3K1O/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCurricular.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1Wxy9MN3QBRswTzS3HXxLOaqHr44nnLaJ/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCreditoPosgrado.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1FTFfmKNAFhjButfQp1VgleAJygNPnA2j/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasTesis.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1-3ctGjC1H1Rg2eQqa1p2cTkGdLpofcqq/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.fabNext.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgTitulacionUnoDirections.actionFrgTitulacionUnoToFrgTitulacionDos())
        }
    }
}