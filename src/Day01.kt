fun main() {
    fun total(input: String): List<Int> {
        return input.split("\n\n")
            .map { elf -> elf.lines().sumOf { it.toInt() } }
    }

    fun part1(testInput: String): Int {
        return total(testInput).max()
    }

    fun part2(testInput: String): Int {
        return total(testInput)
            .sortedDescending()
            .take(3)
            .fold(0) { acc, elem -> acc + elem }
    }

    val testInput = readText("../data/Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readText("../data/Day01")
    println(part1(input))
    println(part2(input))
}
