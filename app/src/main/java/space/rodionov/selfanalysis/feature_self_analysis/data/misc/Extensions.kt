package space.rodionov.selfanalysis.feature_self_analysis.data.misc

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type


fun String.fromStringToStringList() : List<String> {
    val type: Type = TypeToken.getParameterized(List::class.java, String::class.java).type
    return Gson().fromJson(this, type)





//    return listOf("Это", "ёбаная", "заглушка")
}

fun List<String>.fromStringListToString(): String {
    return Gson().toJson(this)
//    return "Заглушка"
}



inline fun <reified T> fromJson(json: String, classList: Class<*>? = null, inputClass: Class<*>? = null): T {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter: JsonAdapter<T>
    if (classList != null && inputClass != null) {
        val type = Types.newParameterizedType(classList, inputClass)
        adapter = moshi.adapter<T>(type)
    } else {
        adapter = moshi.adapter<T>(T::class.java)
    }
    return adapter.fromJson(json) ?: throw Exception("json is invalid")
}

@JvmName("toJson1")
inline fun <reified T> toJson(model: T, classList: Class<*>? = null, inputClass: Class<*>? = null): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter: JsonAdapter<T>
    if (classList != null && inputClass != null) {
        val type = Types.newParameterizedType(classList, inputClass)
        adapter = moshi.adapter<T>(type)
    } else {
        adapter = moshi.adapter<T>(T::class.java)
    }
    return adapter.toJson(model)
}