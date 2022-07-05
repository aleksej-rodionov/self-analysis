package space.rodionov.selfanalysis.feature_self_analysis.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
