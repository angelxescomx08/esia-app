package com.ipn.esiatecamachalco.library.alert

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.library.alert.clases.TypeAlert
import com.ipn.esiatecamachalco.library.alert.clases.gone
import com.ipn.esiatecamachalco.library.alert.clases.visible
import com.ipn.esiatecamachalco.databinding.LayoutAlertBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CustomAlert(val context: Context) {

    private val TAG = this::class.java.simpleName

    val dialog by lazy { Dialog(context, R.style.PopTheme) }
    val alertBinding by lazy { LayoutAlertBinding.inflate(LayoutInflater.from(context)) }

    private var onPositive: () -> Unit = {}
    private var onNegative: () -> Unit = {}

    fun setOnPositive(onReady: () -> Unit) {
        onPositive = onReady
    }

    fun setOnNegative(onReady: () -> Unit) {
        onNegative = onReady
    }

    init {
        alertBinding.imgIcon.visibility = View.GONE
    }

    fun setData(titulo: String, texto: String, positiveBtn: String, negativeBtn: String) {
        alertBinding.title.text = titulo
        alertBinding.message.text = texto
        alertBinding.positiveBtn.text = positiveBtn
        alertBinding.negativeBtn.text = negativeBtn
        alertBinding.positiveBtn.visibility = View.VISIBLE
        alertBinding.negativeBtn.visibility = View.VISIBLE
        alertBinding.message.visibility = View.VISIBLE
    }

    fun setData(titulo: String, texto: String, positiveBtn: String) {
        alertBinding.title.text = titulo
        alertBinding.message.text = texto
        alertBinding.positiveBtn.text = positiveBtn
        alertBinding.message.visibility = View.VISIBLE
        alertBinding.positiveBtn.visibility = View.VISIBLE
        alertBinding.negativeBtn.visibility = View.GONE
    }

    fun setData(titulo: String, positiveBtn: String) {
        alertBinding.title.text = titulo
        alertBinding.positiveBtn.text = positiveBtn
        alertBinding.positiveBtn.visibility = View.VISIBLE
        alertBinding.negativeBtn.visibility = View.GONE
        alertBinding.message.visibility = View.GONE
    }

    fun setData(titulo: String) {
        alertBinding.title.text = titulo
        alertBinding.positiveBtn.visibility = View.GONE
        alertBinding.negativeBtn.visibility = View.GONE
        alertBinding.message.visibility = View.GONE
    }

    fun updateView(icon: Int) = CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {

        alertBinding.imgIcon.visible()
        alertBinding.icon.gone()
        Glide.with(context)
            .load(icon)
            .into(alertBinding.imgIcon)
        alertBinding.background.backgroundTintList = (ContextCompat.getColorStateList(context,R.color.colorPrimary))
        alertBinding.positiveBtn.backgroundTintList = (ContextCompat.getColorStateList(context,R.color.colorPrimary))
    }

    fun updateView(type: TypeAlert) = CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {

        if (type == TypeAlert.Loading) {
            alertBinding.icon.visible()
            alertBinding.imgIcon.gone()
        }
        else {
            alertBinding.imgIcon.visible()
            alertBinding.icon.gone()
            val tipo = when (type) {
                TypeAlert.Success -> R.drawable.ic_done_big
                TypeAlert.Error -> R.drawable.ic_error_outline_big
                TypeAlert.Warning -> R.drawable.ic_warning_big
                TypeAlert.Question -> R.drawable.ic_question_big
                else -> R.drawable.ic_info
            }
            Glide.with(context).asDrawable().load(tipo).into(alertBinding.imgIcon)
        }

        alertBinding.background.backgroundTintList = (ContextCompat.getColorStateList(context,
            when (type) {
                TypeAlert.Success -> R.color.done
                TypeAlert.Error -> R.color.error
                TypeAlert.Warning -> R.color.warning
                //TypeAlert.Question -> R.color.question
                TypeAlert.Info -> R.color.info
                else -> R.color.colorPrimary
            }
        ))

        alertBinding.positiveBtn.backgroundTintList = (ContextCompat.getColorStateList(context,
            when (type) {
                TypeAlert.Success -> R.color.done
                TypeAlert.Error -> R.color.error
                TypeAlert.Warning -> R.color.warning
                TypeAlert.Question -> R.color.question
                TypeAlert.Info -> R.color.info
                else -> R.color.colorPrimary
            }
        ))
    }

    fun show(icon: Int = R.drawable.ic_done_big, cancelable: Boolean = true, closeButton: Boolean = true) {

        updateView(icon)
        setOnClick(closeButton)

        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setContentView(alertBinding.root)
            dialog.setCancelable(cancelable)
            dialog.show()
        }
    }

    fun show(type: TypeAlert = TypeAlert.Info, cancelable: Boolean = true, closeButton: Boolean = true) {

        updateView(type)
        setOnClick(closeButton)

        CoroutineScope(SupervisorJob()).launch(Dispatchers.Main) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setContentView(alertBinding.root)
            dialog.setCancelable(cancelable)
            dialog.show()
        }
    }

    private fun setOnClick(closeButton: Boolean) {
        alertBinding.positiveBtn.setOnClickListener {
            onPositive.invoke()
            if (closeButton) dismiss()
        }

        alertBinding.negativeBtn.setOnClickListener {
            onNegative.invoke()
            if (closeButton) dismiss()
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }
}