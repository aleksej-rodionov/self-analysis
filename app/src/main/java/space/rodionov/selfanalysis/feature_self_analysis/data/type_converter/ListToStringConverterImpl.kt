package space.rodionov.selfanalysis.feature_self_analysis.data.type_converter

import space.rodionov.selfanalysis.feature_self_analysis.domain.util.ListToStringConverter

class ListToStringConverterImpl(

) : ListToStringConverter {

    override fun listToString(list: List<String>): String {
        return "Заглушка"
    }

    override fun stringToList(string: String): List<String> {
        return listOf("Это", "ёбаная", "заглушка")
    }


//    override fun <T> listToString(list: List<T>): String {
//        return list.forEach {
//
//        }
//    }
//
//    override fun <T> stringToList(string: String): List<T> {
//
//    }
}