package util

data class Distance(val row: Int, val column: Int) {

    operator fun unaryMinus() = Distance(-row, -column)

    companion object {

        fun row(row: Int) = Distance(row, 0)

        fun column(column: Int) = Distance(0, column)
    }
}
