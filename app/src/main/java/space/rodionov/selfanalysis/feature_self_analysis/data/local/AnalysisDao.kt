package space.rodionov.selfanalysis.feature_self_analysis.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysis: Note)

    @Update
    suspend fun update(analysis: Note)

    @Delete
    suspend fun delete(analysis: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE (date LIKE '%' || :searchQuery || '%' " +
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
    fun getNotes(searchQuery: String?, emotionFilter: String?): Flow<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>
}