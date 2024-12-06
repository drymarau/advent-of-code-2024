
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

class Day6(private val map: List<List<Char>>, private val row: Int, private val column: Int) {

    fun part1(): Int {
        val positions = buildSet<Position> {
            map.simulate { position, _ -> add(position); false }
        }
        return positions.size
    }

    fun part2(): Int {
        var extraObstacles = AtomicInteger()
        runBlocking(Dispatchers.Default) {
            for (i in map.indices) for (j in map[i].indices) launch {
                val obstacle = when (map[i][j]) {
                    '.' -> Position(i, j)
                    else -> return@launch
                }
                val frequency = mutableMapOf<Pair<Position, Direction>, Int>()
                map.simulate(obstacle) { position, direction ->
                    val key = position to direction
                    frequency[key] = frequency.getOrElse(key) { 0 } + 1
                    when (frequency.getValue(key) > 1) {
                        true -> {
                            extraObstacles.incrementAndGet()
                            true
                        }

                        else -> false
                    }
                }
            }
        }
        return extraObstacles.get()
    }

    private inline fun List<List<Char>>.simulate(
        obstacle: Position? = null,
        onPosition: (Position, Direction) -> Boolean
    ) {
        var position = Position(row, column)
        var direction = Direction.Top
        val b = when (position) {
            obstacle -> false
            else -> onPosition(position, direction)
        }
        if (b) return
        while (true) {
            position = position.next(direction)
            if (position !in this) break
            val c = when (position) {
                obstacle -> '#'
                else -> this[position]
            }
            when (c) {
                '#' -> {
                    position = position.previous(direction)
                    direction = direction.turnRight()
                }

                '.' -> if (onPosition(position, direction)) break
            }
        }
    }

    private data class Position(val row: Int, val column: Int) {

        fun next(direction: Direction) = Position(
            row = row + direction.vertical,
            column = column + direction.horizontal,
        )

        fun previous(direction: Direction) = Position(
            row = row - direction.vertical,
            column = column - direction.horizontal,
        )

        operator fun minus(diff: Diff) = Position(
            row = row - diff.row,
            column = column - diff.column,
        )

        operator fun minus(position: Position) = Diff(
            row = row - position.row,
            column = column - position.column,
        )
    }

    private data class Diff(val row: Int, val column: Int)

    private operator fun List<List<Char>>.contains(position: Position) =
        position.row in indices && position.column in this[position.row].indices

    private operator fun List<List<Char>>.get(position: Position) = this[position.row][position.column]

    private enum class Direction(val vertical: Int, val horizontal: Int) {

        Left(vertical = 0, horizontal = -1),
        Top(vertical = -1, horizontal = 0),
        Right(vertical = 0, horizontal = 1),
        Bottom(vertical = 1, horizontal = 0);

        fun turnRight() = when (this) {
            Left -> Top
            Top -> Right
            Right -> Bottom
            Bottom -> Left
        }
    }
}
