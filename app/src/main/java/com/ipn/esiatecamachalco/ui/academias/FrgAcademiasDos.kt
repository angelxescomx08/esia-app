package com.ipn.esiatecamachalco.ui.academias

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipn.esiatecamachalco.databinding.LayoutFrgAcademiasDosBinding

class FrgAcademiasDos: Fragment() {

    private lateinit var binding: LayoutFrgAcademiasDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgAcademiasDosBinding.inflate(inflater, container, false)
        return binding.root
    }
}