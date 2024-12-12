import okio.FileSystem
import okio.Path.Companion.toPath
import util.Input

class Day8Test : DayTest(part1 = 14, part2 = 34) {

    override fun create(): Day {
        val input = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day8-input.txt".toPath(), ::add)
        }
        return Day8(Input(input))
    }
}
