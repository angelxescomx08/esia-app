package com.ipn.esiatecamachalco.ui.ubicacion

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.ipn.esiatecamachalco.library.alert.clases.KUtils
import com.ipn.esiatecamachalco.databinding.LayoutFrgUbicacionBinding


class FrgUbicacion: Fragment() {

    private val TAG: String = this::class.java.simpleName
    private lateinit var binding: LayoutFrgUbicacionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgUbicacionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.labelDireccion.text = KUtils.fromHtml(
            "<u>Av. Fuente de los Leones 28.<br>Lomas de Tecamachalco, 53950.<br>Naucalpan de juarez, México.</u>"
        )

        binding.imgTacuba.setOnClickListener {
            if (binding.labelTacuba.isVisible) {
                binding.imgTacuba.rotation = 90F
                KUtils.collapse(binding.labelTacuba)
                KUtils.collapse(binding.labelTacuba2)
            }
            else {
                binding.imgTacuba.rotation = 270F
                KUtils.expand(binding.labelTacuba)
                KUtils.expand(binding.labelTacuba2)
            }
        }
        binding.imgCuatroCaminos.setOnClickListener {
            if (binding.labelCuatroCaminos.isVisible) {
                binding.imgCuatroCaminos.rotation = 90F
                KUtils.collapse(binding.labelCuatroCaminos)
                KUtils.collapse(binding.labelCuatroCaminos2)
            }
            else {
                binding.imgCuatroCaminos.rotation = 270F
                KUtils.expand(binding.labelCuatroCaminos)
                KUtils.expand(binding.labelCuatroCaminos2)
            }
        }
        binding.imgChapultepec.setOnClickListener {
            if (binding.labelChapultepec.isVisible) {
                binding.imgChapultepec.rotation = 90F
                KUtils.collapse(binding.labelChapultepec)
                KUtils.collapse(binding.labelChapultepec2)
            }
            else {
                binding.imgChapultepec.rotation = 270F
                KUtils.expand(binding.labelChapultepec)
                KUtils.expand(binding.labelChapultepec2)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isGoogleMapsInstalled()) {
            binding.labelDireccion.setOnClickListener {
                val gmmIntentUri = Uri.parse("https://goo.gl/maps/XGHf1a9fF9AezVcP8")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage(packgName)
                startActivity(mapIntent)
            }
        }
        else {
            binding.labelDireccion.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data = Uri.parse("market://details?id=$packgName")
                startActivity(intent)
            }
        }
    }

    fun isGoogleMapsInstalled(): Boolean {
        return try {

            val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireContext().packageManager.getApplicationInfo(packgName, PackageManager.ApplicationInfoFlags.of(0))
            }
            else {
                requireContext().packageManager.getApplicationInfo(packgName, 0)
            }
            android.util.Log.d(TAG, "DEBUG: Ha traido la info ${info.name}")
            info.enabled
        }
        catch (e: PackageManager.NameNotFoundException) {
            android.util.Log.d(TAG, "DEBUG: Error al verificar Google Maps",e)
            false
        }
    }

    companion object {
        val packgName = "com.google.android.apps.maps"
    }
}