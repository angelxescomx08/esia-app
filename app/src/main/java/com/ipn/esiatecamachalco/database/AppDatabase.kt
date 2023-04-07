package com.ipn.esiatecamachalco.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ipn.esiatecamachalco.library.alert.clases.KModels
import com.ipn.esiatecamachalco.library.alert.clases.KTE

@Database(
    entities = [
        KModels.DirectorioModelROOM::class,
        KModels.UserModelROOM::class,
    ],
    version = KTE.DB_VERSION
)

abstract class AppDatabase: RoomDatabase() {
    companion object {

        private val TAG: String = this::class.java.simpleName

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            android.util.Log.d(TAG, "Proceso 'DB', DEBUG: La base de datos es null ? ${INSTANCE == null}")

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    KTE.DB_NAME
                )
                    .createFromAsset("database/esia_tecamachalco.db")
                    //.setJournalMode(JournalMode.TRUNCATE)
                    //.fallbackToDestructiveMigration()
                    .setJournalMode(JournalMode.TRUNCATE)
                    .enableMultiInstanceInvalidation()
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance

                android.util.Log.d(TAG, "Proceso 'DB', DEBUG: Retorna la intancia nueva")

                instance
            }
        }
    }

    abstract fun interfaceDao(): AppDao

}