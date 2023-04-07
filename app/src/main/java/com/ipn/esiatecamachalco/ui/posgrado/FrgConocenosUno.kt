package com.ipn.esiatecamachalco.ui.posgrado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.databinding.LayoutFrgPosgradoConocenosUnoBinding
import com.ipn.esiatecamachalco.databinding.LayoutFrgPracticasUnoBinding
import com.ipn.esiatecamachalco.ui.menu.FrgPracticasUnoDirections

class FrgConocenosUno: Fragment() {

    private lateinit var binding: LayoutFrgPosgradoConocenosUnoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgPosgradoConocenosUnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabNext.setOnClickListener {
            Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
                .navigate(FrgConocenosUnoDirections.actionFrgConocenosUnoToFrgConocenosDos())
        }
    }
}