package com.ipn.esiatecamachalco.library.alert.clases

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class KTE {
    companion object {

        const val DB_NAME = "db_ipn_esia_tecamachalco"
        const val DB_VERSION = 1

        const val GENERIC_PRIM_KEY = "generic_prim_key"

        const val TB_USER   = "tb_user"
        const val USR_ID    = "usr_id"
        const val USR_TIPO  = "usr_tipo"
        const val USR_NAME  = "usr_name"

        const val TB_DIRECTORIO   = "tb_directorio"
        const val DIREC_SECCION   = "direc_seccion"
        const val DIREC_NOMBRE    = "direc_nombre"
        const val DIREC_CARGO     = "direc_cargo"
        const val DIREC_CORREO    = "direc_correo"
        const val DIREC_EXT       = "direc_ext"
        const val DIREC_IMG       = "direc_img"
    }
}

@BindingAdapter("viewVisibility")
fun viewVisibility(view: View, enable: Boolean) {
    view.visibility = if (enable) View.VISIBLE else View.GONE
}

enum class TypeAlert { Success, Warning, Error, Question, Info, Loading }
enum class EndPoint { Master, Raw }

fun View.gone () {
    post { this.visibility = View.GONE }
}

fun View.visible () {
    post { this.visibility = View.VISIBLE }
}

fun View.invisible () {
    post {  this.visibility = View.INVISIBLE }
}

fun TextInputLayout.check(): Boolean {
    var isOK = false
    val txt = editText?.text.toString()
    if (txt.isBlank()) {
        error = "Campo obligatorio!"
    }
    else if (txt.contains("  ")) {
        error = "Utilice solamente un espacio por palabra."
    }
    else if (txt.startsWith(" ")) {
        error = "Elimine los espacios al inicio."
    }
    else if (txt.endsWith(" ")) {
        error = "Elimine los espacios al final."
    }
    else {
        isOK = true
        error = null
    }
    return isOK
}

fun TextInputLayout.getText(uppercase: Boolean = false): String {
    return if (uppercase) this.editText?.text.toString().uppercase()
    else this.editText?.text.toString()
}

fun TextInputLayout.setText(str: String = "") {
    this.editText?.setText(str)
}