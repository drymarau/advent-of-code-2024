import okio.FileSystem
import okio.Path.Companion.toPath

class Day9Test : DayTest(part1 = 1928L, part2 = 2858L) {

    override fun create(): Day {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day9-input.txt".toPath(), ::append)
        }
        return Day9(input)
    }
}
