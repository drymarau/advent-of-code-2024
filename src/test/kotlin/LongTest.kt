import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.tableOf
import util.concat
import util.length
import kotlin.test.Test

class LongTest {

    @Test
    fun `correctly concatenates two Longs`() {
        tableOf("l", "r", "result")
            .row(0L, 0L, 0L)
            .row(0L, 1L, 1L)
            .row(1L, 0L, 10L)
            .row(1L, 1L, 11L)
            .row(10L, 1L, 101L)
            .forAll { l, r, result -> assertThat(l.concat(r), "result").isEqualTo(result) }
    }

    @Test
    fun `correctly calculates the length of a Long`() {
        tableOf("long", "length")
            .row(0L, 1)
            .row(9L, 1)
            .row(10L, 2)
            .row(99L, 2)
            .row(100L, 3)
            .row(999L, 3)
            .row(1000L, 4)
            .row(9999L, 4)
            .row(10000L, 5)
            .row(99999L, 5)
            .forAll { long, length -> assertThat(long::length).isEqualTo(length) }
    }
}
