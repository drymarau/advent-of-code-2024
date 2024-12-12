import okio.FileSystem
import okio.Path.Companion.toPath

class Day8Test : DayTest(part1 = 14, part2 = 34) {

    override fun create(): Day {
        val lines = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day8-input.txt".toPath(), ::add)
        }
        return Day8(lines)
    }
}
