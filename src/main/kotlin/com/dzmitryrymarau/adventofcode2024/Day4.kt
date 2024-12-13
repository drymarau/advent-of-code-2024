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
import com.dzmitryrymarau.adventofcode2024.util.Direction
import com.dzmitryrymarau.adventofcode2024.util.Distance
import com.dzmitryrymarau.adventofcode2024.util.Position
import com.dzmitryrymarau.adventofcode2024.util.contains
import com.dzmitryrymarau.adventofcode2024.util.get
import com.dzmitryrymarau.adventofcode2024.util.positions

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
}
