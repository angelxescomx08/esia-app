package com.ipn.esiatecamachalco.library.alert.clases

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class KModels {

    @Entity(tableName = KTE.TB_USER)
    data class UserModelROOM(
        @PrimaryKey @ColumnInfo(name = KTE.USR_ID, typeAffinity = ColumnInfo.INTEGER) val id: Int,
        @ColumnInfo(name = KTE.USR_NAME, typeAffinity = ColumnInfo.TEXT)  val nombre: String?,
        @ColumnInfo(name = KTE.USR_TIPO, typeAffinity = ColumnInfo.TEXT)  val tipo: String?,
    )

    @Entity(tableName = KTE.TB_DIRECTORIO)
    data class DirectorioModelROOM(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = KTE.GENERIC_PRIM_KEY, typeAffinity = ColumnInfo.INTEGER)
        @NonNull val primKey: Int,
        @ColumnInfo(name = KTE.DIREC_SECCION, typeAffinity = ColumnInfo.TEXT)    val seccion: String?,
        @ColumnInfo(name = KTE.DIREC_NOMBRE, typeAffinity = ColumnInfo.TEXT)     val nombre: String?,
        @ColumnInfo(name = KTE.DIREC_CARGO, typeAffinity = ColumnInfo.TEXT)      val cargo: String?,
        @ColumnInfo(name = KTE.DIREC_CORREO, typeAffinity = ColumnInfo.TEXT)     val correo: String?,
        @ColumnInfo(name = KTE.DIREC_EXT, typeAffinity = ColumnInfo.INTEGER)     val ext: Int?,
        @ColumnInfo(name = KTE.DIREC_IMG, typeAffinity = ColumnInfo.BLOB)        var img: ByteArray?,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DirectorioModelROOM

            if (primKey != other.primKey) return false
            if (seccion != other.seccion) return false
            if (nombre != other.nombre) return false
            if (cargo != other.cargo) return false
            if (correo != other.correo) return false
            if (ext != other.ext) return false
            if (img != null) {
                if (other.img == null) return false
                if (!img.contentEquals(other.img)) return false
            } else if (other.img != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = primKey
            result = 31 * result + (seccion?.hashCode() ?: 0)
            result = 31 * result + (nombre?.hashCode() ?: 0)
            result = 31 * result + (cargo?.hashCode() ?: 0)
            result = 31 * result + (correo?.hashCode() ?: 0)
            result = 31 * result + (ext ?: 0)
            result = 31 * result + (img?.contentHashCode() ?: 0)
            return result
        }
    }

}