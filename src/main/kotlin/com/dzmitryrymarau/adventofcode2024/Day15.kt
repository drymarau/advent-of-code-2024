package com.dzmitryrymarau.adventofcode2024

import com.dzmitryrymarau.adventofcode2024.util.Direction
import com.dzmitryrymarau.adventofcode2024.util.Position
import com.dzmitryrymarau.adventofcode2024.util.get
import com.dzmitryrymarau.adventofcode2024.util.positionOf
import com.dzmitryrymarau.adventofcode2024.util.set

class Day15(private val lines: Sequence<String>) : Day {

    private val directions = lines
        .dropWhile(String::isNotBlank)
        .flatMap(String::asSequence)
        .map(Direction::from)

    override fun part1(): Number = simulate()

    override fun part2(): Number = simulate(factor = 2)

    private fun simulate(factor: Int = 1): Int {
        require(factor in 1..2) { "Invalid factor $factor." }
        val map = lines.generateMap(factor)
        var robotPosition = map.positionOf { it == ROBOT }
        for (direction in directions) {
            var nextPosition = robotPosition + direction
            when (map[nextPosition]) {
                WALL -> continue
                EMPTY -> {
                    map[nextPosition] = ROBOT
                    map[robotPosition] = EMPTY
                    robotPosition = nextPosition
                }

                BOX, BOXL, BOXR -> {
                    var positions = map.findPositions(nextPosition, direction) ?: continue
                    for (position in positions.asReversed()) {
                        val nextPosition = position + direction
                        map[nextPosition] = map[position]
                        map[position] = EMPTY
                    }
                    map[nextPosition] = ROBOT
                    map[robotPosition] = EMPTY
                    robotPosition = nextPosition
                }
            }
        }
        return map.sumOfCoordinates(factor)
    }

    private fun List<List<Char>>.findPositions(
        position: Position,
        direction: Direction,
    ): List<Position>? = when (val c = this[position]) {
        BOX -> when (val p = findPositions(position + direction, direction)) {
            null -> null
            else -> buildList {
                add(position)
                addAll(p)
            }
        }

        BOXL, BOXR -> when (direction) {
            Direction.Left, Direction.Right -> {
                when (val p = findPositions(position + direction, direction)) {
                    null -> null
                    else -> buildList {
                        add(position)
                        addAll(p)
                    }
                }
            }

            else -> {
                val lPosition = if (c == BOXL) position else position + Direction.Left
                val rPosition = if (c == BOXR) position else position + Direction.Right
                val l = findPositions(lPosition + direction, direction)
                val r = findPositions(rPosition + direction, direction)
                when {
                    l == null || r == null -> null
                    else -> buildList {
                        add(lPosition)
                        add(rPosition)
                        for (p in l) if (p !in this) add(p)
                        for (p in r) if (p !in this) add(p)
                        val s= buildString {
                            for (p in this@buildList) {
                                append(this@findPositions[p])
                            }
                        }
                        println(s)
                    }
                }
            }
        }

        EMPTY -> emptyList()
        else -> null
    }

    private fun Sequence<String>.generateMap(factor: Int) = buildList<MutableList<Char>> {
        for (line in this@generateMap.takeWhile(String::isNotBlank)) {
            this += when (factor) {
                1 -> line.toMutableList()
                else -> MutableList(line.length * factor) {
                    when (val c = line[it / factor]) {
                        BOX -> if (it % factor == 0) BOXL else BOXR
                        ROBOT -> if (it % factor == 0) ROBOT else EMPTY
                        else -> c
                    }
                }
            }
        }
    }

    private fun List<List<Char>>.sumOfCoordinates(factor: Int) = positions
        .filter { this[it] == if (factor == 1) BOX else BOXL }
        .sumOf { it.row * 100 + it.column }

    private val <T> List<Collection<T>>.positions: Sequence<Position>
        get() = sequence {
            for (row in indices) {
                for (column in get(row).indices) {
                    yield(Position(row, column))
                }
            }
        }

    companion object {

        private const val BOX = 'O'
        private const val BOXL = '['
        private const val BOXR = ']'
        private const val WALL = '#'
        private const val ROBOT = '@'
        private const val EMPTY = '.'
    }
}
