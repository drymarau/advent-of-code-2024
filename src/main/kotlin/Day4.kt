import util.Distance
import util.Input
import util.Position

class Day4(private val input: Input) : Day {

    override fun part1(): Number {
        var count = 0
        for (position in input.positions) {
            if (input[position] != 'X') continue
            count += Direction.entries.count { it.matches(position, "XMAS") }
        }
        return count
    }

    override fun part2(): Number {
        var count = 0
        for (position in input.positions) {
            if (!Direction.BottomRight.matches(position)) continue
            if (!Direction.BottomLeft.matches(position + Distance.column(2))) continue
            count++
        }
        return count
    }

    private fun Direction.matches(position: Position): Boolean {
        val term = when (input[position]) {
            'M' -> "MAS"
            'S' -> "SAM"
            else -> return false
        }
        return matches(position, term)
    }

    private fun Direction.matches(position: Position, term: String): Boolean {
        require(term.isNotEmpty()) { "term is empty." }
        if (position + distance * (term.length - 1) !in input) return false
        for ((i, c) in term.withIndex()) {
            if (c != input[position + distance * i]) return false
        }
        return true
    }

    private enum class Direction(val distance: Distance) {

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
}
