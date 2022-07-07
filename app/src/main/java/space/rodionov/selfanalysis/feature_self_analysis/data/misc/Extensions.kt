package space.rodionov.selfanalysis.feature_self_analysis.data.misc



fun String.toStringList() : List<String> {
    return listOf("Это", "ёбаная", "заглушка")
}

fun List<String>.toJson(): String {
    return "Заглушка"
}