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
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoProfesoresBinding

class FrgProfesores: Fragment() {

    private lateinit var binding: LayoutFrgPosgradoProfesoresBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgPosgradoProfesoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtVerMasEscolaridad.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://conacyt.mx/sistema-nacional-de-investigadores/"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMas12.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ipn.mx/investigacion/estimulos/edi/"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMas13.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cofaa.ipn.mx/becas/beca-por-exclusividad.html"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasCuatro.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ipn.mx/investigacion/convocatorias/internas/picpae-registro.html"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasEscolaridadSeccionDos.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ipn.elsevierpure.com/es/searchAll/index/?search=ESIA+TEC&pageSize=25&showAdvanced=false&allConcepts=true&inferConcepts=true&searchBy=PartOfNameOrTitle"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.txtVerMasTres.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1rhFL_wKp9TGkRXNKWX-2l3Bek1S1YmmG/view?usp=sharing\n"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }


        //mios
        binding.verMasReglamentoPosgrado.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/1ZV57ysm4_3iZ69MOmId8Y6ezZHJgJ4Md/view?usp=sharing\n"))
            try {
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App web inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}