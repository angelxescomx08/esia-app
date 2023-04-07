package com.ipn.esiatecamachalco

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ipn.esiatecamachalco.library.alert.clases.TypeAlert
import com.ipn.esiatecamachalco.library.alert.clases.gone
import com.ipn.esiatecamachalco.library.alert.clases.visible
import com.ipn.esiatecamachalco.databinding.ActivityMainBinding
import com.ipn.esiatecamachalco.library.alert.CustomAlert
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG: String = this::class.java.simpleName

    private val viewModel by lazy { ViewModelProvider(viewModelStore,
        ViewModelMain.ViewModelFactory(requireContext()))[ViewModelMain::class.java] }

    private val viewController by lazy { findNavController(R.id.nav_host_fragment_activity_main) }

    private val scope by lazy { CoroutineScope(SupervisorJob()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel.realoadSession = { checkSession() }

        binding.imgDirectorio.setOnClickListener {
            viewController.navigate(R.id.action_directory)
        }

        binding.imgAyuda.setOnClickListener {
            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_help)
        }

        binding.imgFormatos.setOnClickListener {
            viewController.navigate(R.id.action_format)
        }

        binding.imgMenu.setOnClickListener {
            viewController.navigate(R.id.action_user_type)
        }

        binding.imgRutas.setOnClickListener {
            viewController.navigate(R.id.action_location)
        }

        binding.imgAcademias.setOnClickListener {
            viewController.navigate(R.id.action_academias)
        }

        binding.imgSalir.setOnClickListener {
            val user = Firebase.auth.currentUser
            if (user == null){
                viewModel.currentUserType = ViewModelMain.UserType.None
                viewController.navigate(R.id.action_sesion)
            }
            else {
                CustomAlert(this).apply {
                    setData(
                        getString(R.string.app_name),
                        "¿Desea cerrar sesión?",
                        "Si","No"
                    )
                    setOnPositive {
                        FirebaseAuth.getInstance().signOut()
                        checkSession()
                    }
                    show(TypeAlert.Question)
                }
            }
        }

        // This callback will only be called when MyFragment is at least Started.
        val callback = onBackPressedDispatcher.addCallback(this) {
            val result = viewController.navigateUp()
            Log.d(TAG, "$TAG Proceso 'onBackPressedDispatcher', DEBUG: El resultado es $result")
            if (!result) finish()
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    public override fun onStart() {
        super.onStart()
        checkSession()
    }

    private fun checkSession() = scope.launch(Dispatchers.Main) {
        Log.d(TAG, "DEBUG: Revisa la sesion")
        binding.imgAyuda.visible()
        binding.imgSalir.visible()
        //findNavController(R.id.nav_host_fragment_activity_main).graph.clear()
        if (Firebase.auth.currentUser == null){
            Log.d(TAG, "DEBUG: El usuario es null y no hace nada")
            viewModel.currentUserType = ViewModelMain.UserType.None
            viewModel.deleteTableUser()
            binding.imgFormatos.gone()
            binding.imgMenu.gone()
            binding.imgRutas.gone()
            binding.imgDirectorio.gone()
            binding.imgAcademias.gone()
            viewController.navigate(R.id.action_sesion)
        }
        else {
            Log.d(TAG, "DEBUG: Usuario activo y redigige a menu_top")
            //viewModel.getUserType()
            Toast.makeText(requireContext(), "Bienvenido.", Toast.LENGTH_SHORT).show()
            binding.imgFormatos.visible()
            binding.imgMenu.visible()
            binding.imgRutas.visible()
            binding.imgDirectorio.visible()
            binding.imgAcademias.visible()
            viewController.navigate(R.id.action_user_type)
        }
    }

    //private fun requireActivity(): Activity = this

    private fun requireContext(): Context = this
}