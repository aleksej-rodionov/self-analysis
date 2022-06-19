package space.rodionov.selfanalysis.feature_self_analysis.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.feature_self_analysis.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [AnalysisEntity::class], version = 1, exportSchema = false)
abstract class AnalysisDatabase : RoomDatabase() {

    abstract fun analysisDao(): AnalysisDao

    class Callback @Inject constructor(
        private val database: Provider<AnalysisDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().analysisDao()

            applicationScope.launch {
                dao.deleteAll()

            }
        }
    }

}