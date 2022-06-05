package space.rodionov.selfanalysis.feature_self_analysis.domain.model

import space.rodionov.selfanalysis.feature_self_analysis.data.local.AnalysisEntity

data class Analysis(
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
    val date: String,
    val id: Int? = null
) {
    fun toAnalysisEntity() : AnalysisEntity {
        return AnalysisEntity(
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
            date
        )
    }
}
