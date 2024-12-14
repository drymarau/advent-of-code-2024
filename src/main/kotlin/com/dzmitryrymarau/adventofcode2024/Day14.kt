/*
 * Copyright 2024 Dzmitry Rymarau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dzmitryrymarau.adventofcode2024

import com.dzmitryrymarau.adventofcode2024.util.Distance
import com.dzmitryrymarau.adventofcode2024.util.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.math.abs

class Day14(lines: Sequence<String>) : Day {

    private val bathroom = lines.take(1).map(::Bathroom)
    private val robots = lines.drop(1).map(::Robot)

    override fun part1(): Number = simulate(bathroom.first(), 100)

    override fun part2(): Number = runBlocking(Dispatchers.Default) {
        val bathroom = bathroom.first()
        val (seconds, _) = (1..bathroom.rows * bathroom.columns)
            .map { async { it to simulate(bathroom, it) } }
            .awaitAll()
            .minBy { (_, factor) -> factor }
        seconds
    }

    private fun simulate(bathroom: Bathroom, seconds: Int): Int {
        val map = buildMap<Position, Int> {
            for ((position, velocity) in robots) {
                val (row, column) = position + velocity * seconds
                var finalRow = row % bathroom.rows
                if (finalRow < 0) finalRow = bathroom.rows - abs(finalRow)
                var finalColumn = column % bathroom.columns
                if (finalColumn < 0) finalColumn = bathroom.columns - abs(finalColumn)
                val finalPosition = Position(finalRow, finalColumn)
                this[finalPosition] = (this[finalPosition] ?: 0) + 1
            }
        }
        return bathroom.quadrants.fold(1) { acc, (rows, columns) ->
            var sum = 0
            for (row in rows) {
                for (column in columns) {
                    val position = Position(row, column)
                    sum += map.getOrElse(position) { 0 }
                }
            }
            acc * sum
        }
    }

    private data class Bathroom(val rows: Int, val columns: Int) {

        data class Quadrant(val rows: IntRange, val columns: IntRange)

        val quadrants = buildSet {
            val top = 0..<rows / 2
            val bottom = (rows / 2 + 1)..<rows
            val left = 0..<columns / 2
            val right = (columns / 2 + 1)..<columns
            this += Quadrant(top, left)
            this += Quadrant(top, right)
            this += Quadrant(bottom, left)
            this += Quadrant(bottom, right)
        }

        constructor(line: String) : this(
            parts = line.splitToSequence(',', limit = 2)
                .map(String::toInt)
                .toList(),
        )

        private constructor(parts: List<Int>) : this(rows = parts[1], columns = parts[0])
    }

    private data class Robot(val position: Position, val velocity: Distance) {

        constructor(line: String) : this(checkNotNull(RobotRegex.matchEntire(line)))

        private constructor(result: MatchResult) : this(
            numbers = result.groupValues.asSequence()
                .drop(1)
                .map(String::toInt)
                .toList(),
        )

        private constructor(numbers: List<Int>) : this(
            position = Position(numbers[1], numbers[0]),
            velocity = Distance(numbers[3], numbers[2]),
        )

        companion object {

            private val RobotRegex = Regex("""^p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)$""")
        }
    }
}
