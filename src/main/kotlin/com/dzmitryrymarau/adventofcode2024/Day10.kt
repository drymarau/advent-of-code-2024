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
import com.dzmitryrymarau.adventofcode2024.util.nextDigit
import com.dzmitryrymarau.adventofcode2024.util.positions

class Day10(private val lines: List<String>) : Day {

    constructor(lines: Sequence<String>) : this(lines.toList())

    override fun part1(): Number = count(::mutableSetOf)

    override fun part2(): Number = count(::mutableListOf)

    private fun count(block: () -> MutableCollection<Position>): Int {
        var count = 0
        for (position in lines.positions.filter { lines[it] == '0' }) {
            val collection = block()
            count(position, block = collection::add)
            count += collection.size
        }
        return count
    }

    private fun count(
        position: Position,
        current: Char = lines[position],
        block: (Position) -> Unit,
    ) {
        if (lines[position] != current) return
        if (current == '9') {
            block(position)
            return
        }
        val current = current.nextDigit()
        for (distance in distances) {
            val position = position + distance
            if (position !in lines) continue
            count(position, current, block)
        }
    }

    private val distances =
        sequenceOf(Distance.row(1), Distance.row(-1), Distance.column(1), Distance.column(-1))
}
