import okio.FileSystem
import okio.Path.Companion.toPath

class Day6Test : DayTest(part1 = 41, part2 = 6) {

    override fun create(): Day {
        var row = -1
        var column = -1
        val map = mutableListOf<List<Char>>()
        val directions = "<^>v"
        FileSystem.RESOURCES.readByUtf8LineIndexed("day6-input.txt".toPath()) { i, line ->
            map += line.map { it }
            directions.forEach { c ->
                val index = line.indexOf(c)
                if (index >= 0) {
                    row = i
                    column = index
                }
            }
        }
        return Day6(map, row, column)
    }
}
