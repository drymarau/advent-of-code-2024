import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.measureTimedValue

class Day1Test {

    private lateinit var day1: Day1

    @BeforeTest
    fun setUp() {
        val l = mutableListOf<Int>()
        val r = mutableListOf<Int>()
        val regex = Regex("\\s+")
        FileSystem.RESOURCES.readByUtf8Line("day1-input.txt".toPath()) {
            val parts = it.splitToSequence(regex)
                .map(String::toInt)
                .toList()
            l += parts[0]
            r += parts[1]
        }
        day1 = Day1(l.asSequence(), r.asSequence())
    }

    @Test
    fun `correctly calculates part1`() {
        val result = measureTimedValue(day1::part1)
        assertThat(result::value).isEqualTo(11)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }

    @Test
    fun `correctly calculates part2`() {
        val result = measureTimedValue(day1::part2)
        assertThat(result::value).isEqualTo(31)
        assertThat(result::duration).isLessThan(5.milliseconds)
    }
}
