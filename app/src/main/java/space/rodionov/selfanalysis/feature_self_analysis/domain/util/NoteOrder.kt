package space.rodionov.selfanalysis.feature_self_analysis.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    class Situation(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Situation -> Situation(orderType)
            is Date -> Date(orderType)
        }
    }
}
