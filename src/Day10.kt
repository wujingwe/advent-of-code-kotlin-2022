object Day10 {

    private fun ops(inputs: List<String>): List<Int> {
        return inputs.flatMap { s ->
            val tokens = s.split(" ")
            when (tokens[0]) {
                "noop" -> listOf(0)
                "addx" -> listOf(0, tokens[1].toInt())
                else -> emptyList()
            }
        }.runningFold(1) { acc, elem -> acc + elem }
    }

    fun part1(inputs: List<String>): Int {
        val res = ops(inputs)
        return (20..220 step 40).sumOf { it * res[it - 1] }
    }

    fun part2(inputs: List<String>): String {
        return ops(inputs).filterIndexed { index, _ ->
            index < 240
        }.foldIndexed(StringBuilder()) { index, acc, x ->
            val position = index % 40
            val c = if (position in x - 1..x + 1) '#' else '.'
            acc.append(c)
        }.toString()
    }
}

fun main() {

    fun part1(inputs: List<String>): Int {
        return Day10.part1(inputs)
    }

    fun part2(inputs: List<String>): String {
        return Day10.part2(inputs)
    }

    val testInput = readInput("../data/Day10_test")
    check(part1(testInput) == 13140)

    part2(testInput).chunked(40).forEach {
        println(it)
    }
    val image =
        "##..##..##..##..##..##..##..##..##..##.." +
        "###...###...###...###...###...###...###." +
        "####....####....####....####....####...." +
        "#####.....#####.....#####.....#####....." +
        "######......######......######......####" +
        "#######.......#######.......#######....."
    check(part2(testInput) == image)

    val input = readInput("../data/Day10")
    println(part1(input))

    part2(input).chunked(40).forEach {
        println(it)
    }
}
