import util.Distance
import util.Position
import util.whole

class Day13(lines: Sequence<String>) : Day {

    private val inputs = lines.chunked(4, ::Input)

    override fun part1(): Number = inputs.sumOf(Input::solve)

    override fun part2(): Number = inputs.sumOf { it.solve(correction = 10000000000000L) }

    private data class Input(val a: Distance, val b: Distance, val prize: Position) {

        constructor(lines: List<String>) : this(
            a = distance(lines[0]),
            b = distance(lines[1]),
            prize = prize(lines[2]),
        )

        fun solve(correction: Long = 0L): Long {
            val pRow = prize.row + correction
            val pColumn = prize.column + correction
            val bPresses = (a.column * pRow - a.row * pColumn) / (b.row * a.column - b.column * a.row).toDouble()
            val aPresses = (pRow - bPresses * b.row) / a.row.toDouble()
            if (!aPresses.whole || !bPresses.whole) return 0L
            return (aPresses * 3 + bPresses * 1).toLong()
        }

        companion object {

            private val DistanceRegex = Regex("""^Button [A|B]: X\+(\d+), Y\+(\d+)$""")
            private val PrizeRegex = Regex("""^Prize: X=(\d+), Y=(\d+)$""")

            private fun distance(line: String): Distance {
                val result = requireNotNull(DistanceRegex.matchEntire(line))
                val (row, column) = result.destructured
                return Distance(row.toInt(), column.toInt())
            }

            private fun prize(line: String): Position {
                val result = requireNotNull(PrizeRegex.matchEntire(line))
                val (row, column) = result.destructured
                return Position(row.toInt(), column.toInt())
            }
        }
    }
}
