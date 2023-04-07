package com.ipn.esiatecamachalco.library.alert.clases

import android.content.SharedPreferences
import android.app.Activity
import android.content.Context
import java.util.HashSet

class SharePreference private constructor(context: Context) {

    val pref: SharedPreferences?

    fun saveData(key: String?, value: String?) {
        val editSP = pref!!.edit()
        editSP.putString(key, value)
        editSP.apply()
    }

    fun saveData(key: String?, value: Boolean?) {
        val editSP = pref!!.edit()
        editSP.putBoolean(key, value!!)
        editSP.apply()
    }

    fun saveData(key: String?, value: Int) {
        val editSP = pref!!.edit()
        editSP.putInt(key, value)
        editSP.apply()
    }

    fun saveData(key: String?, value: Set<String?>?) {
        val editSP = pref!!.edit()
        editSP.putStringSet(key, value)
        editSP.apply()
    }

    fun saveData(key: String?, value: Float) {
        val editSP = pref!!.edit()
        editSP.putFloat(key, value)
        editSP.apply()
    }

    fun getStrData(key: String?): String? {
        return if (pref != null) pref.getString(key, "") else ""
    }

    fun getBooData(key: String?): Boolean {
        return pref != null && pref.getBoolean(key, false)
    }

    fun getBooDataSendFake(key: String?): Boolean {
        return pref != null && pref.getBoolean(key, true)
    }

    fun getIntData(key: String?): Int {
        return pref?.getInt(key, 0) ?: 0
    }

    fun getFloatData(key: String?): Double {
        return pref?.getFloat(key, 0f)?.toDouble() ?: 0.0
    }

    fun getFloatDataNull(key: String?, coordenada: Float): Float {
        return pref?.getFloat(key, coordenada) ?: 0f
    }

    fun getStringSet(key: String?): Set<String>? {
        return if (pref != null) pref.getStringSet(key, HashSet()) else HashSet()
    }

    fun clearSharedPreferencesForPictures() {
        val editor = pref!!.edit()
        editor.clear()
        editor.commit()
    }

    fun initDataInstallApp() {
        if (pref != null) {
            println("App recien instalada")
            if (pref.getInt("isAppInstalled", 0) != 1) {
                val editSP = pref.edit()
                editSP.putInt("isAppInstalled", 1)
                editSP.apply()
            }
        }
    }

    companion object {
        private const val _PREFERENCE = "SHARE_PREFERENCE"
        private var preference: SharePreference? = null
        fun getInstance(act: Activity): SharePreference? {
            if (preference == null) preference = SharePreference(act)
            return preference
        }

        fun getInstance(context: Context): SharePreference {
            if (preference == null) preference = SharePreference(context)
            return preference!!
        }
    }

    init {
        pref = context.getSharedPreferences(_PREFERENCE, Context.MODE_PRIVATE)
    }
}