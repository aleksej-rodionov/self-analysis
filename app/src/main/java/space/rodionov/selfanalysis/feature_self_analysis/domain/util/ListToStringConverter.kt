package space.rodionov.selfanalysis.feature_self_analysis.domain.util

interface ListToStringConverter {

    fun listToString(list: List<String>) : String

    fun stringToList(string: String) : List<String>

//    fun <T> listToString(list: List<T>) : String
//
//    fun <T> stringToList(string: String) : List<T>
}