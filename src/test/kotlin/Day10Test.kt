import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import util.Input
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day10Test {

    private lateinit var day10: Day10

    @BeforeTest
    fun setUp() {
        val input = buildList {
            FileSystem.RESOURCES.readByUtf8Line("day10-input.txt".toPath(), ::add)
        }
        day10 = Day10(Input(input))
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day10::part1)
        assertThat(result::value).isEqualTo(36)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day10::part2)
        assertThat(result::value).isEqualTo(81)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
