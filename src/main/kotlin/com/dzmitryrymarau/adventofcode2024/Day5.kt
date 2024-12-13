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

class Day5(lines: Sequence<String>) : Day {

    private val rules: Map<Int, Set<Int>>
    private val updates: List<List<Int>>

    init {
        val r = mutableMapOf<Int, Set<Int>>()
        val u = mutableListOf<List<Int>>()
        for (line in lines) {
            when {
                '|' in line -> {
                    val (beforePage, page) = line.splitToSequence('|', limit = 2)
                        .map(String::toInt)
                        .toList()
                    val set = r.getOrPut(beforePage, ::mutableSetOf)
                    r.put(beforePage, set + page)
                }

                ',' in line -> {
                    u += line.splitToSequence(',')
                        .map(String::toInt)
                        .toList()
                }
            }
        }
        rules = r
        updates = u
    }

    override fun part1(): Number = updates.asSequence()
        .filter(::good)
        .sumOf(::middleElement)

    override fun part2(): Number = updates.asSequence()
        .filterNot(::good)
        .map { it.sortedWith(comparator) }
        .sumOf(::middleElement)

    private fun good(update: List<Int>): Boolean {
        val seen = mutableSetOf<Int>()
        update.forEach {
            val rule = rules.getOrElse(it, ::emptySet)
            val intersection = rule.intersect(seen)
            if (intersection.isNotEmpty()) return false
            seen.add(it)
        }
        return true
    }

    private fun <T> middleElement(list: List<T>) = list[list.size / 2]

    private val comparator = Comparator<Int> { a, b ->
        when (b) {
            in rules.getOrElse(a, ::mutableSetOf) -> -1
            else -> 0
        }
    }
}
