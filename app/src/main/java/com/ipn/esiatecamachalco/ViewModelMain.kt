package com.ipn.esiatecamachalco

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.database.AppDao
import com.ipn.esiatecamachalco.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ViewModelMain(val dao: AppDao) : ViewModel() {

    enum class UserType {Alumno,Docente,Posgrado,None}

    var showPosgrado = false

    var currentUserType = UserType.None
        set(value) {
            field = value
            _isUserTypeSelected.postValue(field != UserType.None)
        }

    private val scope by lazy { CoroutineScope(SupervisorJob()) }

    private val _isProgressVisible =  MutableLiveData(false)
    val isProgressVisible: LiveData<Boolean> = _isProgressVisible
    fun setProgressVisible(bool: Boolean) = _isProgressVisible.postValue(bool)

    private val _isUserTypeSelected =  MutableLiveData(false)
    val isUserTypeSelected: LiveData<Boolean> = _isUserTypeSelected

    var realoadSession: () -> Unit = {}

    fun getUserType() { //= scope.launch(Dispatchers.IO) {
        val user = dao.getUser()
        currentUserType = when (user.tipo) {
            UserType.Alumno.name -> UserType.Alumno
            UserType.Docente.name -> UserType.Docente
            UserType.Posgrado.name -> UserType.Posgrado
            else  -> UserType.Posgrado
        }
    }

    fun deleteTableUser() = scope.launch(Dispatchers.IO) {
        dao.deleteTableUser()
    }

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val dao = AppDatabase.getDatabase(context).interfaceDao()
            return ViewModelMain(dao) as T
        }
    }
}