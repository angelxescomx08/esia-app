package com.ipn.esiatecamachalco.ui.posgrado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoConocenosDosBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoConocenosUnoBinding
import com.ipn.esiatecamachalco.ui.menu.FrgPracticasUnoDirections

class FrgConocenosDos: Fragment() {

    private lateinit var binding: LayoutFrgPosgradoConocenosDosBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgPosgradoConocenosDosBinding.inflate(inflater, container, false)
        return binding.root
    }
}