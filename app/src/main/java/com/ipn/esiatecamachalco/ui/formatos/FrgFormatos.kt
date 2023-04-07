package com.ipn.esiatecamachalco.ui.formatos

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ipn.esiatecamachalco.databinding.LayoutFrgFormatosBinding


class FrgFormatos: Fragment() {

    private lateinit var binding: LayoutFrgFormatosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgFormatosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adpFormatos = AdpFormatos()
        binding.recyclerFormatos.adapter = adpFormatos

        adpFormatos.submitList(listOf(
            ModelFormato("Prácticas profesionales", "https://drive.google.com/drive/folders/1uoJG7sS15A6twA7VPgjCewoa5F11uh94?usp=sharing"),
            ModelFormato("Servicio social", "https://drive.google.com/drive/folders/1HnjV5WdiPeDyPL_oziRxhc1RTn-e0-av?usp=sharing"),
            ModelFormato("Tutorías", "https://drive.google.com/drive/folders/16KGNC95ifazSMRMgcuzK5G42dMmSc09h?usp=sharing"),
            ModelFormato("UDI", "https://drive.google.com/drive/folders/1dgid_0cpscwC31lp-0ATeJU8rJlpSqsR?usp=sharing"),
            ModelFormato("Gestión escolar", "https://drive.google.com/drive/folders/1q5_xTNNY7jTjxY0XafbwnEoYtHKPP877?usp=sharing"),
            ModelFormato("Trámite de boletas", "https://docs.google.com/forms/d/e/1FAIpQLSd_UOKMiwzwD75mhq8nLfGb-zpA8ag9yg_rTMQEGc94tF_Rmg/closedform"),
            ModelFormato("Formulario de afiliación al IMSS", "https://docs.google.com/forms/d/e/1FAIpQLSdE-QJ9w5uGW2pjf-EcPjkydyD_oVmutFFzbrGmJbudCd2O4Q/viewform"),
        ))

        adpFormatos.onItemClicked = { modelFormato, code ->
            when (code) {
                1 -> {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(modelFormato.link)
                    try {
                        startActivity(i)
                    }
                    catch (e: ActivityNotFoundException) {
                        Snackbar.make(binding.root,"App compatible no encontrada.", Snackbar.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                }
                0 -> {
                    copyClipboard("link",modelFormato.link,"Enlace copiado.")
                }
            }
        }
    }

    private fun copyClipboard(label:String, text: String, snackbar: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root,snackbar, Snackbar.LENGTH_SHORT).show()
    }
}