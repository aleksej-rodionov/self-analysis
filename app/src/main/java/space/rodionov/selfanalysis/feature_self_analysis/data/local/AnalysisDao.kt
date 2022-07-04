package space.rodionov.selfanalysis.feature_self_analysis.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import space.rodionov.selfanalysis.data.Note

@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysis: AnalysisEntity)

    @Update
    suspend fun update(analysis: AnalysisEntity)

    @Delete
    suspend fun delete(analysis: AnalysisEntity)

    @Query("DELETE FROM analysis_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM analysis_table WHERE (date LIKE '%' || :searchQuery || '%' " +
            " OR situation LIKE '%' || :searchQuery || '%' " +
            " OR emotions LIKE '%' || :searchQuery || '%'" +
            " OR feelings LIKE '%' || :searchQuery || '%' " +
            " OR inTheBody LIKE '%' || :searchQuery || '%' " +
            " OR wantedToDo LIKE '%' || :searchQuery || '%' " +
            " OR whatDoesTheFeelingMean LIKE '%' || :searchQuery || '%' " +
            " OR thoughts LIKE '%' || :searchQuery || '%' " +
            " OR fears LIKE '%' || :searchQuery || '%' " +
            " OR askingFromHP LIKE '%' || :searchQuery || '%' " +
            " OR innerCritic LIKE '%' || :searchQuery || '%' " +
            " OR lovingParent LIKE '%' || :searchQuery || '%') " +
            " AND emotions LIKE '%' || :emotionFilter || '%'" +
            "ORDER BY id DESC")
    fun getNotes(searchQuery: String, emotionFilter: String): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM analysis_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<AnalysisEntity>>
}