fun main() {

    infix fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.first && this.last >= other.last
    }

    fun part1(inputs: List<String>): Int {
        return inputs.count { s ->
            val (a, b) = s.split(",")
            val (a1, a2) = a.split("-")
            val (b1, b2) = b.split("-")

            val range1 = a1.toInt()..a2.toInt()
            val range2 = b1.toInt()..b2.toInt()

            range1 contains range2 || range2 contains range1
        }
    }

    fun part2(inputs: List<String>): Int {
        return inputs.count { s ->
            val (a, b) = s.split(",")
            val (a1, a2) = a.split("-")
            val (b1, b2) = b.split("-")
            val range1 = a1.toInt()..a2.toInt()
            val range2 = b1.toInt()..b2.toInt()
            (range1 intersect range2).isNotEmpty()
        }
    }

    val testInput = readInput("../data/Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("../data/Day04")
    println(part1(input))
    println(part2(input))
}
