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
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoAlumnoDosBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoConocenosDosBinding

class FrgAlumnosDos: Fragment() {

    private lateinit var binding: LayoutFrgPosgradoAlumnoDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgPosgradoAlumnoDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasEscolaridad.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1-DRDlTjA4VgAxpo4H4GFrQsIyslHaVuv?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCurricular.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1ToveqpIY1Z10rgwnX5kWI8M9vF52lXIy?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCreditoPosgrado.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1wyegQb8UVdlXjHaXQ74gaEGywFbWwIO-?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasTesis.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1K2ggJ-lyRKHm2RVpgSGy46iKOGzZ7izu?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasEscolaridadDos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1QgOljQqIizDKwnohn5g-Axj1Iut0XH7F?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCurricularDos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1ZV57ysm4_3iZ69MOmId8Y6ezZHJgJ4Md/view?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCreditoPosgradoDos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1m0yWnJF54H8U9HIrCmGfIOlZCaZV9ZAR/edit?usp=sharing&ouid=110957765882058906807&rtpof=true&sd=true"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasTesisDos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/drive/folders/1jdSG1y1Ll-nWqvHTYYJGcm5Z4pcDdfUe?usp=sharing"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}