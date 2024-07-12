package commons.extensions

import domain.models.Oudler

fun List<Oudler>.getRequiredPoints() : Int {
    return when(this.size){
        0 -> 56
        1 -> 51
        2 -> 41
        3 -> 36
        else -> 0
    }
}