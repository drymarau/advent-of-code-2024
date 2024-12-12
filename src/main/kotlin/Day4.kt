import util.Distance
import util.Position
import util.contains
import util.get
import util.positions

class Day4(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    override fun part1(): Number {
        var count = 0
        for (position in lines.positions) {
            if (lines[position] != 'X') continue
            count += Direction.entries.count { it.matches(position, "XMAS") }
        }
        return count
    }

    override fun part2(): Number {
        var count = 0
        for (position in lines.positions) {
            if (!Direction.BottomRight.matches(position)) continue
            if (!Direction.BottomLeft.matches(position + Distance.column(2))) continue
            count++
        }
        return count
    }

    private fun Direction.matches(position: Position): Boolean {
        val term = when (lines[position]) {
            'M' -> "MAS"
            'S' -> "SAM"
            else -> return false
        }
        return matches(position, term)
    }

    private fun Direction.matches(position: Position, term: String): Boolean {
        require(term.isNotEmpty()) { "term is empty." }
        if (position + distance * (term.length - 1) !in lines) return false
        for ((i, c) in term.withIndex()) {
            if (c != lines[position + distance * i]) return false
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
