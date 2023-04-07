package com.ipn.esiatecamachalco.ui.sesion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.ViewModelMain
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.library.alert.clases.KUtils
import com.ipn.esiatecamachalco.library.alert.clases.TypeAlert
import com.ipn.esiatecamachalco.library.alert.clases.check
import com.ipn.esiatecamachalco.databinding.LayoutFrgSesionBinding
import com.ipn.esiatecamachalco.library.alert.CustomAlert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FrgSesion: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private lateinit var binding: LayoutFrgSesionBinding

    private val scope by lazy { CoroutineScope(SupervisorJob()) }
    private val vmm: ViewModelMain by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgSesionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shake = AnimationUtils.loadAnimation(view.context, R.anim.shake)

        /*
        * Mod de login
        * */
        binding.inputUsuario.editText?.setText("2025020207")
        binding.inputContra.editText?.setText("ESIA Tecamachalco")
        // */

        binding.mainConstraintLayout.setOnClickListener {
            limpiarFocus()
        }

        binding.mainConstraintLayout.setOnLongClickListener {
            limpiarFocus()
            binding.inputUsuario.editText?.setText("")
            binding.inputContra.editText?.setText("")
            binding.inputUsuario.startAnimation(shake)
            binding.inputContra.startAnimation(shake)
            true
        }

        binding.btnLogin.setOnClickListener {
            KUtils.hideKeyboard(activity)

            if (binding.inputUsuario.check() && binding.inputContra.check()) {
                signIn("${binding.inputUsuario.editText?.text.toString()}@gmail.com",binding.inputContra.editText?.text.toString())
            }
            else {
                CustomAlert(requireContext()).apply {
                    setData("Campos incompletos","Revise la información ingresada","Cerrar","Salir de la app")
                    setOnNegative { requireActivity().finish() }
                    show(TypeAlert.Warning)
                }
            }

        }

        val controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)

        binding.btnRegistro.setOnClickListener {
            controller.navigate(FrgSesionDirections.actionToRegistro())
        }
    }

    private fun limpiarFocus() {
        binding.inputUsuario.editText?.clearFocus()
        binding.inputUsuario.clearFocus()
        binding.inputContra.editText?.clearFocus()
        binding.inputContra.clearFocus()
        KUtils.hideKeyboard(activity)
    }

    private fun signIn(email: String, password: String) = scope.launch(Dispatchers.IO) {
        vmm.setProgressVisible(true)
        val isOK = KUtils.startGooglePing(requireContext())
        if (isOK) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    vmm.setProgressVisible(false)
                    view?.context?.let { ctx ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            vmm.dao.insert(KModels.UserModelROOM(0,email,vmm.currentUserType.name))
                            vmm.realoadSession.invoke()
                        }
                        else {
                            val shake = AnimationUtils.loadAnimation(ctx, R.anim.shake)
                            binding.inputUsuario.startAnimation(shake)
                            binding.inputContra.startAnimation(shake)
                        }
                    }
                }
                .addOnFailureListener { ex ->
                    ex.printStackTrace()
                    vmm.setProgressVisible(false)
                    view?.context?.let { ctx ->

                        val txt = when (ex.message.toString()) {
                            "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                "Este usuario no existe"
                            }
                            "The password is invalid or the user does not have a password." -> {
                                "Usuario o contraseña incorrectos."
                            }
                            else -> ex.message.toString()
                        }

                        CustomAlert(ctx).apply {
                            setData(
                                getString(R.string.app_name),
                                txt,
                                "Cerrar"
                            )
                            show(TypeAlert.Error)
                        }
                    }
                }
        }
        else {
            vmm.setProgressVisible(false)
            CustomAlert(requireContext()).apply {
                setData(
                    getString(R.string.app_name),
                    "No tienes conexión a internet",
                    "OK"
                )
                show(R.drawable.ic_wifi_off)
            }
        }
    }

}