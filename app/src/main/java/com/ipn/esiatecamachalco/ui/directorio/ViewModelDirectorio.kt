package com.ipn.esiatecamachalco.ui.directorio

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.database.AppDao
import kotlinx.coroutines.flow.Flow

class ViewModelDirectorio(private val dao: AppDao) : ViewModel() {

    fun getPaging(cadena: String = ""): Flow<PagingData<KModels.DirectorioModelROOM>> {
        val pagSizeCustom = 20
        val maxElem = pagSizeCustom + (2*pagSizeCustom)

        return Pager (
            config = PagingConfig(pageSize = pagSizeCustom, maxSize = maxElem, enablePlaceholders = true),
            pagingSourceFactory = {
                when (cadena) {
                    "" -> dao.getAllDirectorio()
                    else -> dao.getSearchDirectorio("%$cadena%")
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    class ViewModelDirectorioFactory(private val repo: AppDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ViewModelDirectorio(repo) as T
        }
    }
}