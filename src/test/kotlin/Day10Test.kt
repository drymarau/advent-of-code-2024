import okio.FileSystem
import okio.Path.Companion.toPath

class Day10Test : DayTest(part1 = 36, part2 = 81) {

    override fun create(): Day {
        val lines = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day10-input.txt".toPath(), ::add)
        }
        return Day10(lines)
    }
}
