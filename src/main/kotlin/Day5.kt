class Day5(lines: Sequence<String>) : Day {

    private val rules: Map<Int, Set<Int>>
    private val updates: List<List<Int>>

    init {
        val r = mutableMapOf<Int, Set<Int>>()
        val u = mutableListOf<List<Int>>()
        for (line in lines) {
            when {
                '|' in line -> {
                    val (beforePage, page) = line.splitToSequence('|', limit = 2)
                        .map(String::toInt)
                        .toList()
                    val set = r.getOrPut(beforePage, ::mutableSetOf)
                    r.put(beforePage, set + page)
                }

                ',' in line -> {
                    u += line.splitToSequence(',')
                        .map(String::toInt)
                        .toList()
                }
            }
        }
        rules = r
        updates = u
    }

    override fun part1(): Number = updates.asSequence()
        .filter(::good)
        .sumOf(::middleElement)

    override fun part2(): Number = updates.asSequence()
        .filterNot(::good)
        .map { it.sortedWith(comparator) }
        .sumOf(::middleElement)

    private fun good(update: List<Int>): Boolean {
        val seen = mutableSetOf<Int>()
        update.forEach {
            val rule = rules.getOrElse(it, ::emptySet)
            val intersection = rule.intersect(seen)
            if (intersection.isNotEmpty()) return false
            seen.add(it)
        }
        return true
    }

    private fun <T> middleElement(list: List<T>) = list[list.size / 2]

    private val comparator = Comparator<Int> { a, b ->
        when (b) {
            in rules.getOrElse(a, ::mutableSetOf) -> -1
            else -> 0
        }
    }
}
