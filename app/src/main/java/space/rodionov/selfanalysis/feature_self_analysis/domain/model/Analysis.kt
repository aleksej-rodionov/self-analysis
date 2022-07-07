package space.rodionov.selfanalysis.feature_self_analysis.domain.model

import space.rodionov.selfanalysis.feature_self_analysis.data.local.Note
import space.rodionov.selfanalysis.feature_self_analysis.data.misc.fromStringListToString

data class Analysis(
    val situation: String,
    val emotions: List<String>/*? =null*/, //todo поменять list на set
    val feelings: String/*? = null*/,
    val inTheBody: String/*? = null*/,
    val wantedToDo: String? = null,
    val whatDoesTheFeelingMean: String? = null,
    val thoughts: String? = null,
    val fears: String? = null,
    val askingFromHP: String? = null,
    val innerCritic: String? = null,
    val lovingParent: String? = null,
    val date: String,
    val id: Int? = null
) {
    fun toAnalysisEntity() : Note {
        return Note(
            situation,
            emotions.fromStringListToString(), //todo serialize list/set
            feelings,
            inTheBody,
            wantedToDo,
            whatDoesTheFeelingMean,
            thoughts,
            fears,
            askingFromHP,
            innerCritic,
            lovingParent,
            date
        )
    }
}

class InvalidAnalysisException(message: String): Exception(message)
