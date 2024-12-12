package util

enum class Direction(val distance: Distance) {

    Top(row = -1, column = 0),
    Left(row = 0, column = -1),
    Right(row = 0, column = 1),
    Bottom(row = 1, column = 0),
    TopLeft(row = -1, column = -1),
    TopRight(row = -1, column = 1),
    BottomLeft(row = 1, column = -1),
    BottomRight(row = 1, column = 1);

    constructor(row: Int, column: Int) : this(Distance(row, column))
}
