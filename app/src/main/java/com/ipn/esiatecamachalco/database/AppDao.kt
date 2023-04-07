package com.ipn.esiatecamachalco.database

import androidx.paging.PagingSource
import androidx.room.*
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.library.alert.clases.KTE

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: KModels.UserModelROOM)

    @Query("DELETE FROM ${KTE.TB_USER}")
    suspend fun deleteTableUser()

    @Query("SELECT * FROM ${KTE.TB_USER} LIMIT 1")
    fun getUser(): KModels.UserModelROOM

    @Query("SELECT * FROM ${KTE.TB_DIRECTORIO} ORDER BY " +
            "${KTE.GENERIC_PRIM_KEY} ASC, " +
            "${KTE.DIREC_SECCION} ASC, ${KTE.DIREC_CARGO} ASC")
    fun getAllDirectorio(): PagingSource<Int, KModels.DirectorioModelROOM>

    @Query("SELECT * FROM ${KTE.TB_DIRECTORIO} " +
            "WHERE " +
            "${KTE.DIREC_NOMBRE} LIKE :search " +
            "OR ${KTE.DIREC_SECCION} LIKE :search " +
            "OR ${KTE.DIREC_NOMBRE} LIKE :search " +
            "OR ${KTE.DIREC_CARGO} LIKE :search " +
            "OR ${KTE.DIREC_CORREO} LIKE :search " +
            "OR ${KTE.DIREC_EXT} LIKE :search " +
            "ORDER BY " +
            "${KTE.GENERIC_PRIM_KEY} ASC, " +
            "${KTE.DIREC_SECCION} ASC, ${KTE.DIREC_CARGO} ASC, ${KTE.DIREC_NOMBRE} DESC")
    fun getSearchDirectorio(search: String): PagingSource<Int, KModels.DirectorioModelROOM>
}