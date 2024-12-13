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

class Day3(private val input: String) : Day {

    constructor(lines: Sequence<String>) : this(lines.joinToString())

    private val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")

    override fun part1(): Number = regex.findAll(input).sumOf(::mul)

    override fun part2(): Number {
        var apply = true
        return regex.findAll(input)
            .onEach {
                when (it.value) {
                    "do()" -> apply = true
                    "don't()" -> apply = false
                }
            }
            .filter { apply }
            .sumOf(::mul)
    }

    private fun mul(result: MatchResult): Int = when (result.value) {
        "do()", "don't()" -> 0
        else -> result.groupValues[1].toInt() * result.groupValues[2].toInt()
    }
}
