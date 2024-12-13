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
import com.dzmitryrymarau.adventofcode2024.util.contains
import com.dzmitryrymarau.adventofcode2024.util.get
import com.dzmitryrymarau.adventofcode2024.util.positions

class Day8(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    private val map = buildMap {
        for (position in lines.positions) {
            when (val c = lines[position]) {
                '.' -> continue
                else -> this[c] = (this[c] ?: listOf<Position>()) + position
            }
        }
    }

    override fun part1(): Number = calculate()

    override fun part2(): Number = calculate(resonant = true)

    private fun calculate(resonant: Boolean = false): Int {
        val antiNodes = buildSet {
            tailrec fun iterate(
                position: Position,
                distance: Distance,
                operation: Position.(Distance) -> Position,
            ) {
                val antiNode = position.operation(distance)
                if (antiNode !in lines) return
                this += antiNode
                if (resonant) iterate(antiNode, distance, operation)
            }
            for ((_, positions) in map) {
                for (i in 0..positions.lastIndex - 1) {
                    for (j in i + 1..positions.lastIndex) {
                        if (resonant) {
                            this += positions[i]
                            this += positions[j]
                        }
                        val distance = positions[i] - positions[j]
                        iterate(positions[i], distance, Position::plus)
                        iterate(positions[j], distance, Position::minus)
                    }
                }
            }
        }
        return antiNodes.size
    }
}
