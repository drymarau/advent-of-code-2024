import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day11Test {

    private lateinit var day11: Day11

    @BeforeTest
    fun setUp() {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day11-input.txt".toPath(), ::append)
        }
        day11 = Day11(input)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day11::part1)
        assertThat(result::value).isEqualTo(55312L)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day11::part2)
        assertThat(result::value).isEqualTo(65601038650482L)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
