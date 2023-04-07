package com.ipn.esiatecamachalco.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipn.esiatecamachalco.library.alert.clases.KUtils
import com.ipn.esiatecamachalco.databinding.LayoutFrgTramitesEscolaresTresBinding

class FrgTramitesEscolaresTres: Fragment() {

    private lateinit var binding: LayoutFrgTramitesEscolaresTresBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgTramitesEscolaresTresBinding.inflate(inflater, container, false)
        return binding.root
    }

}