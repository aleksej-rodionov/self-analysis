package space.rodionov.selfanalysis.feature_self_analysis.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import space.rodionov.selfanalysis.feature_self_analysis.domain.model.Analysis


@Entity(tableName = "analysis_table")
@Parcelize
data class AnalysisEntity( // todo в конце переименовать в Note чтобы не было конфликта с уе выложенной версией Database
    val situation: String,
    val emotions: String/*? = null*/,
    val feelings: String/*? = null*/,
    val inTheBody: String/*? = null*/,
    val wantedToDo: String? = null,
    val whatDoesTheFeelingMean: String? = null,
    val thoughts: String? = null,
    val fears: String? = null,
    val askingFromHP: String? = null,
    val innerCritic: String? = null,
    val lovingParent: String? = null,
    val date: String, /*= System.currentTimeMillis().toString(),*/ /*Long = System.currentTimeMillis(),*/
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    /*val dateFormatted: String
        get() = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(date)*/

    fun toAnalysis() : Analysis {
        return Analysis(
            situation,
            emotions,
            feelings,
            inTheBody,
            wantedToDo,
            whatDoesTheFeelingMean,
            thoughts,
            fears,
            askingFromHP,
            innerCritic,
            lovingParent,
            date,
            id
        )
    }
}

