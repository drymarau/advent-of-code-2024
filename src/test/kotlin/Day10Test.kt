import okio.FileSystem
import okio.Path.Companion.toPath
import util.Input

class Day10Test : DayTest(part1 = 36, part2 = 81) {

    override fun create(): Day {
        val input = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day10-input.txt".toPath(), ::add)
        }
        return Day10(Input(input))
    }
}
