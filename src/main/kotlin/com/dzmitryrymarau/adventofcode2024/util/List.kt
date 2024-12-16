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
package com.dzmitryrymarau.adventofcode2024.util

val <T> List<T>.head get() = first()

val <T> List<T>.tail get() = slice(1..lastIndex)

val List<String>.positions: Sequence<Position>
    get() = sequence {
        for (row in indices) {
            for (column in get(row).indices) {
                yield(Position(row, column))
            }
        }
    }

operator fun List<String>.get(position: Position) = this[position.row][position.column]

fun List<String>.getOrNull(position: Position): Char? {
    val row = getOrNull(position.row) ?: return null
    return row.getOrNull(position.column)
}

operator fun List<String>.contains(position: Position) =
    position.row in indices && position.column in this[position.row].indices

operator fun <E> List<List<E>>.get(position: Position) =
    this[position.row][position.column]

operator fun <E> List<MutableList<E>>.set(position: Position, value: E): E =
    this[position.row].set(position.column, value)

inline fun <E> List<List<E>>.positionOf(predicate: (E) -> Boolean): Position {
    for (row in indices) {
        for (column in this[row].indices) {
            if (predicate(this[row][column])) {
                return Position(row, column)
            }
        }
    }
    return Position(-1, -1)
}

inline fun <E> List<List<E>>.prettyPrint(block: (E) -> Char) {
    val s = buildString {
        for (row in this@prettyPrint.indices) {
            for (column in this@prettyPrint[row].indices) {
                append(block(this@prettyPrint[row][column]))
            }
            appendLine()
        }
    }
    println(s)
}

fun List<List<Char>>.prettyPrint() = prettyPrint { it }
