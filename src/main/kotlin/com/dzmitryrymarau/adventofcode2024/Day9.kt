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

class Day9(private val input: String) : Day {

    constructor(lines: Sequence<String>) : this(lines.joinToString())

    override fun part1(): Number {
        val fileSystem = input.generateFileSystem()
        var freeSpaceIndex = fileSystem.indexOfFirst { it == null }
        var fileIndex = fileSystem.indexOfLast { it != null }
        val indices = fileSystem.indices
        while (freeSpaceIndex < fileIndex) {
            fileSystem[freeSpaceIndex] = fileSystem[fileIndex]
            fileSystem[fileIndex] = null
            while (freeSpaceIndex in indices && fileSystem[freeSpaceIndex] != null) freeSpaceIndex++
            while (fileIndex in indices && fileSystem[fileIndex] == null) fileIndex--
        }
        return fileSystem.calculateChecksum()
    }

    override fun part2(): Number {
        val fileSystem = input.generateFileSystem()
        var freeSpaceIndex = fileSystem.indexOfFirst { it == null }
        var fileIndex = fileSystem.indexOfLast { it != null }
        val indices = fileSystem.indices
        while (freeSpaceIndex < fileIndex) {
            val fileId = fileSystem[fileIndex]
            var fileSize = 0
            while (fileIndex in indices && fileSystem[fileIndex] == fileId) {
                fileSize++
                fileIndex--
            }
            fileIndex++
            while (freeSpaceIndex + fileSize - 1 in indices) {
                val space = fileSystem.subList(freeSpaceIndex, freeSpaceIndex + fileSize)
                if (space.all { it == null }) {
                    repeat(fileSize) {
                        fileSystem[freeSpaceIndex + it] = fileId
                        fileSystem[fileIndex + it] = null
                    }
                    break
                } else {
                    freeSpaceIndex++
                    if (freeSpaceIndex >= fileIndex) break
                }
            }
            freeSpaceIndex = fileSystem.indexOfFirst { it == null }
            while (--fileIndex in indices && fileSystem[fileIndex] == null) Unit
        }
        return fileSystem.calculateChecksum()
    }

    private fun String.generateFileSystem() = buildList {
        input.forEachIndexed { i, c ->
            val s = when (i % 2) {
                0 -> "${i / 2}"
                else -> null
            }
            repeat(c.digitToInt()) { add(s) }
        }
    }.toMutableList()

    private fun List<String?>.calculateChecksum() = asSequence()
        .mapIndexed { i, s -> i * (s?.toLong() ?: 0L) }
        .sum()
}
