import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThanOrEqualTo
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTimedValue

abstract class DayTest(
    private val path: String,
    private val part1: Number,
    private val part2: Number,
    private val factory: (lines: Sequence<String>) -> Day,
) {

    private lateinit var day: Day

    @BeforeTest
    fun setUp() {
        day = factory(FileSystem.RESOURCES.readByUtf8LineToSequence(path.toPath()))
    }

    @Test
    fun `correctly calculates part 1`() = `correctly calculates part`(part1, day::part1)

    @Test
    fun `correctly calculates part 2`() = `correctly calculates part`(part2, day::part2)

    private inline fun `correctly calculates part`(expected: Number, block: () -> Number) {
        val result = measureTimedValue(block)
        assertThat(result::value).isEqualTo(expected)
        assertThat(result::duration).isLessThanOrEqualTo(1.seconds)
    }
}
