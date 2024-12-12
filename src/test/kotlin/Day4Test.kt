import okio.FileSystem
import okio.Path.Companion.toPath

class Day4Test : DayTest(part1 = 18, part2 = 9) {

    override fun create(): Day {
        val lines = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day4-input.txt".toPath(), ::add)
        }
        return Day4(lines)
    }
}
