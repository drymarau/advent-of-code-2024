import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

class Day7Test {

    private lateinit var day7: Day7

    @BeforeTest
    fun setUp() {
        val lines = sequence {
            FileSystem.RESOURCES.readByUtf8Line("day7-input.txt".toPath()) { yield(it) }
        }
        day7 = Day7(lines)
    }

    @Test
    fun `correctly calculates part 1`() {
        val result = measureTimedValue(day7::part1)
        assertThat(result::value).isEqualTo(3749L)
        assertThat(result::duration).isLessThan(1.seconds)
    }

    @Test
    fun `correctly calculates part 2`() {
        val result = measureTimedValue(day7::part2)
        assertThat(result::value).isEqualTo(11387L)
        assertThat(result::duration).isLessThan(1.seconds)
    }
}
