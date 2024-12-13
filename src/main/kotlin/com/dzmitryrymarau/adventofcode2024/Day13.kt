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
import com.dzmitryrymarau.adventofcode2024.util.whole

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
            val bPresses =
                (a.column * pRow - a.row * pColumn) / (b.row * a.column - b.column * a.row).toDouble()
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
