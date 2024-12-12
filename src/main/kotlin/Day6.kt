import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import util.Direction
import util.Position
import util.contains
import util.get
import util.positions
import java.util.concurrent.atomic.AtomicInteger

class Day6(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    override fun part1(): Number {
        val positions = buildSet<Position> {
            lines.simulate { position, _ -> add(position); false }
        }
        return positions.size
    }

    override fun part2(): Number {
        var extraObstacles = AtomicInteger()
        runBlocking(Dispatchers.Default) {
            for (position in lines.positions.filter { lines[it] == '.' }) launch {
                val frequency = mutableMapOf<Pair<Position, Direction>, Int>()
                lines.simulate(position) { position, direction ->
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

    private inline fun List<String>.simulate(
        obstacle: Position? = null,
        onPosition: (Position, Direction) -> Boolean
    ) {
        var position = positions.first { get(it) == '^' }
        var direction = Direction.Top
        val b = when (position) {
            obstacle -> false
            else -> onPosition(position, direction)
        }
        if (b) return
        while (true) {
            position = position + direction
            if (position !in this) break
            val c = when (position) {
                obstacle -> '#'
                else -> this[position]
            }
            when (c) {
                '#' -> {
                    position = position - direction
                    direction = direction.turnRight()
                }

                '.' -> if (onPosition(position, direction)) break
            }
        }
    }

    private fun Direction.turnRight() = when (this) {
        Direction.Left -> Direction.Top
        Direction.Top -> Direction.Right
        Direction.Right -> Direction.Bottom
        Direction.Bottom -> Direction.Left
        Direction.TopLeft -> Direction.TopRight
        Direction.TopRight -> Direction.BottomRight
        Direction.BottomLeft -> Direction.TopLeft
        Direction.BottomRight -> Direction.BottomLeft
    }
}
