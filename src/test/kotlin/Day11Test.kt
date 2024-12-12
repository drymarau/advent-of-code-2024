import okio.FileSystem
import okio.Path.Companion.toPath

class Day11Test : DayTest(part1 = 55312L, part2 = 65601038650482L) {

    override fun create(): Day {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day11-input.txt".toPath(), ::append)
        }
        return Day11(input)
    }
}
