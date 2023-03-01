package space.rodionov.selfanalysis.feature_self_analysis.data.misc

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import space.rodionov.selfanalysis.util.Constants.TAG_DEBUG

fun List<String>.toJson(): String {
    val gson = Gson()
    val json = gson.toJson(this)
    return json
}

fun String.toStringList() : List<String> {
    val gson = Gson()
    val emosType = object  : TypeToken<List<String>>() {}.type
    val emoList: List<String> = gson.fromJson(this, emosType)
    return emoList
}