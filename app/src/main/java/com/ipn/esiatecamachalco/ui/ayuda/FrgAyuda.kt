package com.ipn.esiatecamachalco.ui.ayuda

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ipn.esiatecamachalco.databinding.LayoutFrgAyudaBinding


class FrgAyuda: Fragment() {

    private lateinit var binding: LayoutFrgAyudaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgAyudaBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun copyClipboard(label:String, text: String, snackbar: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Snackbar.make(binding.root,snackbar, Snackbar.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = Intent(Intent.ACTION_DIAL)

        binding.imgEmailResponsable.setOnClickListener {
            val intentMail = Intent(Intent.ACTION_SENDTO)
            intentMail.data = Uri.parse("mailto:santiago@ipn.mx")
            try {
                startActivity(intentMail)
            }
            catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de correo inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.imgCopyResponsable.setOnClickListener { copyClipboard("email","santiago@ipn.mx", "Correo copiado.") }

        binding.imgPhoneEmergencia.setOnClickListener {
            intent.data = Uri.parse("tel:53712250")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de teléfono inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.imgCopyEmergencia.setOnClickListener { copyClipboard("phone","53712250", "Teléfono copiado.") }

        binding.imgPhoneBomberos.setOnClickListener {
            intent.data = Uri.parse("tel:52942416")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de teléfono inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.imgCopyBomberos.setOnClickListener { copyClipboard("phone","52942416", "Teléfono copiado.") }

        binding.imgPhoneBomberos.setOnLongClickListener {
            intent.data = Uri.parse("tel:53731122")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de teléfono inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
            true
        }
        binding.imgCopyBomberos.setOnLongClickListener {
            copyClipboard("phone","53731122", "Teléfono copiado.")
            true
        }

        binding.imgPhoneCruzRoja.setOnClickListener {
            intent.data = Uri.parse("tel:55604859")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de teléfono inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.imgCopyCruzRoja.setOnClickListener { copyClipboard("phone","55604859", "Teléfono copiado.") }

        binding.imgPhoneProteccionCivil.setOnClickListener {
            intent.data = Uri.parse("tel:55607221")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Snackbar.make(binding.root,"App de teléfono inhabilitada.", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.imgCopyProteccionCivil.setOnClickListener { copyClipboard("phone","55607221", "Teléfono copiado.") }
    }
}