package com.ipn.esiatecamachalco.ui.sesion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.ViewModelMain
import com.ipn.esiatecamachalco.library.alert.clases.TypeAlert
import com.ipn.esiatecamachalco.library.alert.clases.getText
import com.ipn.esiatecamachalco.library.alert.clases.setText
import com.ipn.esiatecamachalco.databinding.LayoutFrgRegistroBinding
import com.ipn.esiatecamachalco.library.alert.CustomAlert

class FrgRegistro: Fragment() {

    private lateinit var binding: LayoutFrgRegistroBinding
    private val vmm: ViewModelMain by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputUsuario.setText("2025020207")
        binding.inputContra.setText("ESIA Tecamachalco")
        binding.inputContraVerif.setText("ESIA Tecamachalco")

        binding.btnLogin.setOnClickListener {
            if (validaCampos())
            Firebase.auth.createUserWithEmailAndPassword("${binding.inputUsuario.getText()}@gmail.com", binding.inputContra.getText())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        vmm.realoadSession.invoke()
                    }
                    else {
                        task.exception?.let { ex ->
                            val txt = when (ex.message.toString()) {
                                "The email address is already in use by another account." -> {
                                    "Número de boleta ya registrada."
                                }
                                "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> {
                                    "No se ha podido establecer conexión con el servidor."
                                }
                                else -> ex.message.toString()
                            }

                            CustomAlert(requireContext()).apply {
                                setData("Error",txt,"Cerrar")
                                show(TypeAlert.Error)
                            }

                            ex.printStackTrace()
                        }
                    }
                }
        }


    }

    private fun validaCampos(): Boolean {
        var isOK = false
        val alert = CustomAlert(requireContext())
        if (binding.inputContra.getText() != binding.inputContraVerif.getText()) {
            alert.setData("Error","Las contraseñas no coinciden","Cerrar")
        }
        else if (binding.inputUsuario.getText().length != 10) {
            alert.setData("Error","La boleta debe tener 10 dígitos","Cerrar")
        }
        else if (binding.inputContra.getText().length < 5) {
            alert.setData("Error","La contraseña debe tener al menos 10 dígitos","Cerrar")
        }
        else if (binding.inputContra.getText().length > 20) {
            alert.setData("Error","La contraseña debe tener máximo 20 dígitos","Cerrar")
        }
        else if (binding.inputContra.getText().contains("  ")) {
            alert.setData("Error","La contraseña no debe contener dos espacios consecutivos","Cerrar")
        }
        else {
            isOK = true
        }
        if (!isOK) alert.show(TypeAlert.Error)
        return isOK
    }
}