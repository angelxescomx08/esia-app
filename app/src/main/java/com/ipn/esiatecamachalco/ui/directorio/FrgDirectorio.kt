package com.ipn.esiatecamachalco.ui.directorio

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.ipn.esiatecamachalco.Application
import com.ipn.esiatecamachalco.R
import com.ipn.esiatecamachalco.library.alert.clases.KUtils
import com.ipn.esiatecamachalco.library.alert.clases.manageTextWatcher
import com.ipn.esiatecamachalco.database.AppDatabase
import com.ipn.esiatecamachalco.databinding.LayoutFrgDirectorioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class FrgDirectorio : Fragment() {

    private val TAG: String = this::class.java.simpleName
    private val scope by lazy { CoroutineScope(SupervisorJob()) }

    private val viewModel by lazy {
        ViewModelProvider(this,
            ViewModelDirectorio.ViewModelDirectorioFactory(
                //(activity?.application as Application).database.interfaceDao()
                AppDatabase.getDatabase(requireContext()).interfaceDao()
            )
        )[ViewModelDirectorio::class.java]
    }

    private lateinit var binding: LayoutFrgDirectorioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LayoutFrgDirectorioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shake = AnimationUtils.loadAnimation(view.context, R.anim.shake)
        val recyclerAdapter = AdpDirectorio()
        binding.recycler.adapter = recyclerAdapter

        binding.swipeRefresh.setOnRefreshListener {
            KUtils.hideKeyboard(activity)
            binding.recycler.startAnimation(shake)
            recyclerAdapter.refresh()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                scope.launch(Dispatchers.IO) {
                    val cadena = s.toString().trim()
                    if (cadena.isBlank()) {
                        viewModel.getPaging().collectLatest { recyclerAdapter.submitData(it) }
                    }
                    else {
                        viewModel.getPaging(cadena).collectLatest { recyclerAdapter.submitData(it) }
                    }
                }
            }
        }

        binding.inputBuscar.editText?.manageTextWatcher(textWatcher)

        recyclerAdapter.onItemClicked = {
            Log.d(TAG, "DEBUG: Ha presionado ${it.nombre}, ${it.correo}")
            if (it.correo.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "No hay correo registrado", Toast.LENGTH_SHORT).show()
            }
            else {
                val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Email", it.correo)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(requireContext(), "Correo agregado al portapapeles", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerAdapter.addLoadStateListener { loadStates ->
            Log.d(TAG, "DEBUG: ${loadStates.refresh}, ${loadStates.refresh is LoadState.Loading}")
            binding.swipeRefresh.run {
                val isLoading = loadStates.refresh is LoadState.Loading
                post { isRefreshing = isLoading }
                if (!isLoading) {
                    val vis = if (recyclerAdapter.itemCount == 0) {
                        View.VISIBLE
                    }
                    else {
                        View.GONE
                    }
                    binding.imgNoData.visibility = vis
                    binding.txtNoData.visibility = vis
                }
            }

            /*// show a retry button outside the list when refresh hits an error
            retryButton.isVisible = it.refresh is LoadState.Error

            // swipeRefreshLayout displays whether refresh is occurring
            swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading

            // show an empty state over the list when loading initially, before items are loaded
            emptyState.isVisible = it.refresh is LoadState.Loading && adapter.itemCount == 0*/
        }

        lifecycleScope.launchWhenCreated {
            recyclerAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                /*.collectLatest { loadStates ->
                    binding.swipeRefresh.run {
                        post { isRefreshing = loadStates.refresh is LoadState.Loading }
                    }
                    binding.recyclerView.run { post { scrollToPosition(0) } }
                }*/

            viewModel.getPaging().collectLatest {
                recyclerAdapter.submitData(it)
            }
        }
    }
}