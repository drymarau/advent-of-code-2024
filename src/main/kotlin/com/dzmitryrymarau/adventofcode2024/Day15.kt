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
            while (map[nextPosition] == BOX) {
                nextPosition += direction
            }
            if (map[nextPosition] == EMPTY) {
                map[robotPosition] = EMPTY
                robotPosition += direction
                when (val o = map[robotPosition]) {
                    BOX -> {
                        map[robotPosition] = ROBOT
                        map[nextPosition] = o
                    }

                    EMPTY -> map[robotPosition] = ROBOT
                    else -> Unit
                }
            }
        }
        return map.sumOfCoordinates(factor)
    }

    private fun Sequence<String>.generateMap(factor: Int) = buildList<MutableList<Char>> {
        for (line in this@generateMap.takeWhile(String::isNotBlank)) {
            this += when (factor) {
                1 -> line.toMutableList()
                else -> MutableList(line.length * factor) {
                    when (val c = line[it / factor]) {
                        BOX -> when (it % factor) {
                            0 -> BOX_L
                            else -> BOX_R
                        }

                        ROBOT -> when (it % factor) {
                            0 -> ROBOT
                            else -> EMPTY
                        }

                        else -> c
                    }
                }
            }
        }
    }

    private fun List<List<Char>>.sumOfCoordinates(factor: Int) = positions
        .filter { this[it] == if (factor == 1) BOX else BOX_L }
        .sumOf { it.row * 100 + it.column }

    private fun prettyPrint(map: List<List<Char>>) {
        val s = buildString {
            for (row in map.indices) {
                for (column in map[row].indices) {
                    append(map[row][column])
                }
                appendLine()
            }
        }
        print(s)
    }

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
        private const val BOX_L = '['
        private const val BOX_R = ']'
        private const val ROBOT = '@'
        private const val EMPTY = '.'
    }
}
