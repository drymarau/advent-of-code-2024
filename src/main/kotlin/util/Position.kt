package util

data class Position(val row: Int, val column: Int) {

    operator fun plus(distance: Distance) = Position(row = row + distance.row, column = column + distance.column)

    operator fun minus(distance: Distance) = Position(row = row - distance.row, column = column - distance.column)

    operator fun plus(direction: Direction) = plus(direction.distance)

    operator fun minus(direction: Direction) = minus(direction.distance)

    operator fun minus(other: Position) = Distance(row = row - other.row, column = column - other.column)
}
