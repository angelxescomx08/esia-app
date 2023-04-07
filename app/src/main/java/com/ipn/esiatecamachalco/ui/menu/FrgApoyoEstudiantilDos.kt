package com.ipn.esiatecamachalco.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipn.esiatecamachalco.databinding.LayoutFrgApoyoDosBinding

class FrgApoyoEstudiantilDos: Fragment() {

    private lateinit var binding: LayoutFrgApoyoDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgApoyoDosBinding.inflate(inflater, container, false)
        return binding.root
    }
}