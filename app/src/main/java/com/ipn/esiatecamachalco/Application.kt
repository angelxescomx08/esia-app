package com.ipn.esiatecamachalco

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
//import com.ipn.esiatecamachalco.clases.*
import com.ipn.esiatecamachalco.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream


class Application : Application() {

    private val LIGHT_MODE = "light"
    private val DARK_MODE = "dark"

    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        applyTheme(LIGHT_MODE)

        /*val preference = SharePreference.getInstance(this)
        if (preference.getBooData("isDarkMode")) {
            applyTheme(DARK_MODE)
        }
        else {
            applyTheme(LIGHT_MODE)
        }

        val array = listOf(
            "Apoyo estudiantil.pdf",
            "Tramites escolares.pdf",
            "Planta general.pdf",
            "Academias.pdf",
            "Aulas prefabricadas.pdf",
            "Biblioteca.pdf",
            "CAE.pdf",
            "Cafeteria.pdf",
            "Edificio 1.pdf",
            "Edificio 2.pdf",
            "Gobierno.pdf",
            "Laboratorio multiproposito.pdf",
            "Laboratorios.pdf",
            //"",
        )

        if (!File(this@Application.filesDir, array[0]).exists()) {
            CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
                array.forEach {
                    generaArchivos(it)
                }
            }
        }*/

    }

    private fun generaArchivos(name: String) {
        try {
            this.assets.open(name).use { entrada ->
                this.openFileOutput(
                    File(this.filesDir, name).name,
                    Context.MODE_PRIVATE
                ).use { out ->
                    entrada.copyTo(out)
                    out.flush()
                }
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun applyTheme(themePref: String) {
        when (themePref) {
            LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}