fun main() {

    fun Char.toPriority(): Int = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> 0
    }

    fun part1(inputs: List<String>): Int {
        return inputs.map { s ->
            val (first, second) = s.chunked(s.length / 2)
            (first.toSet() intersect second.toSet()).first().toPriority()
        }.reduce { acc, elem -> acc + elem }
    }

    fun part2(inputs: List<String>): Int {
        return inputs.chunked(3)
            .map { (first, second, third) ->
                (first.toSet() intersect second.toSet() intersect third.toSet())
                    .first()
                    .toPriority()
            }.reduce { acc, elem -> acc + elem }
    }

    val testInput = readInput("../data/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("../data/Day03")
    println(part1(input))
    println(part2(input))
}
