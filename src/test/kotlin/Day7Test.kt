import okio.FileSystem
import okio.Path.Companion.toPath

class Day7Test : DayTest(part1 = 3749L, part2 = 11387L) {

    override fun create(): Day {
        val lines = sequence {
            FileSystem.RESOURCES.readByUtf8Line("day7-input.txt".toPath()) { yield(it) }
        }
        return Day7(lines)
    }
}
