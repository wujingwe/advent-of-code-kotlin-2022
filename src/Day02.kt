fun main() {
    fun String.toShape(): Shape = when(this) {
        "A", "X" -> Shape.Rock
        "B", "Y" -> Shape.Paper
        else -> Shape.Scissor
    }

    fun shapeScore(shape: Shape): Int {
        return shape.score
    }

    fun outcomeScore(opponent: Shape, me: Shape): Int {
        return if (me > opponent) {
            6 // win
        } else if (me == opponent) {
            3 // draw
        } else {
            0 // lose
        }
    }

    fun part1(inputs: List<String>): Int {
        var total = 0
        inputs.forEach {
            val (a, b) = it.split(" ")
            val opponent = a.toShape()
            val me = b.toShape()
            total += shapeScore(me)
            total += outcomeScore(opponent, me)
        }
        return total
    }

    fun part2(inputs: List<String>): Int {
        var total = 0
        inputs.forEach {
            val (a, b) = it.split(" ")
            val opponent = a.toShape()
            val me = when (b) {
                "X" -> { // lose
                    when (opponent) {
                        Shape.Rock -> Shape.Scissor
                        Shape.Paper -> Shape.Rock
                        else -> Shape.Paper
                    }
                }
                "Y" -> opponent
                else -> { // win
                    when (opponent) {
                        Shape.Rock -> Shape.Paper
                        Shape.Paper -> Shape.Scissor
                        else -> Shape.Rock
                    }
                }
            }
            total += shapeScore(me)
            total += outcomeScore(opponent, me)
        }
        return total
    }

    val testInput = readInput("../data/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("../data/Day02")
    println(part1(input))
    println(part2(input))
}

sealed class Shape(val score: Int) {
    object Rock: Shape(1)
    object Paper : Shape(2)
    object Scissor : Shape(3)

    operator fun compareTo(opponent: Shape): Int {
        if (this == opponent) {
            return 0 // draw
        }
        if ((opponent is Rock && this is Paper) ||
            (opponent is Paper && this is Scissor) ||
            (opponent is Scissor && this is Rock)
        ) {
           return 1 // win
        }
        return -1 // lose
    }
}
