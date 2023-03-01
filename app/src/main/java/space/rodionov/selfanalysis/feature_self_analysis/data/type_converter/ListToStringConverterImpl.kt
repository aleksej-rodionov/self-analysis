package space.rodionov.selfanalysis.feature_self_analysis.data.type_converter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.rodionov.selfanalysis.feature_self_analysis.domain.util.ListToStringConverter
import java.lang.reflect.Type

//class ListToStringConverterImpl(
//
//) : ListToStringConverter {
//
//    private val gson = Gson()
//
//    override fun listToString(list: List<String>): String {
//        return gson.toJson(list)
//    }
//
//    override fun stringToList(string: String): List<String> {
//        val type: Type = object : TypeToken<List<String>?>() {}.type
//        return gson.fromJson(string, type)
//    }
//
//
////    override fun <T> listToString(list: List<T>): String {
////        return list.forEach {
////
////        }
////    }
////
////    override fun <T> stringToList(string: String): List<T> {
////
////    }
//}