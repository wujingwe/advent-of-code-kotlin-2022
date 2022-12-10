object Day10 {

    private fun ops(inputs: List<String>): List<Int> {
        var X = 1
        return inputs.flatMap { s ->
            val tokens = s.split(" ")
            when (tokens[0]) {
                "noop" -> listOf(X)
                "addx" -> listOf(X, X).also { X += tokens[1].toInt() }
                else -> emptyList()
            }
        }
    }

    fun part1(inputs: List<String>): Int {
        return ops(inputs).filterIndexed { index, _ ->
            index <= 220
        }.foldIndexed(0) { index, acc, x ->
            val circle = index + 1
            acc + if (circle == 20 || circle == 60 || circle == 100 || circle == 140 || circle == 180 || circle == 220) {
                circle * x
            } else {
                0
            }
        }
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
