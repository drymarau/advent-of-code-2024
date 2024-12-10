import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day9Test {

    private lateinit var day9: Day9

    @BeforeTest
    fun setUp() {
        val input = buildString {
            FileSystem.RESOURCES.readByUtf8Line("day9-input.txt".toPath(), ::append)
        }
        day9 = Day9(input)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day9::part1)
        assertThat(result::value).isEqualTo(1928L)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day9::part2)
        assertThat(result::value).isEqualTo(2858L)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
