import java.util.Stack

object Day13 {

    sealed class Node {
        object LEFT : Node()
        class Sequence(val elements: List<Node>) : Node()
        class Digit(val element: Int) : Node()

        operator fun compareTo(other: Node): Int {
            if (this is Digit && other is Digit) {
                return this.element - other.element
            } else if (this is Sequence && other is Digit) {
                return compareTo(Sequence(listOf(other)))
            } else if (this is Digit && other is Sequence) {
                return Sequence(listOf(this)).compareTo(other)
            } else { // Both list
                val left = this as Sequence
                val right = other as Sequence
                var i = 0
                while (i < left.elements.size && i < right.elements.size) {
                    val ret = left.elements[i].compareTo(right.elements[i])
                    if (ret != 0) {
                        return ret
                    }
                    i++
                }
                return if (left.elements.size == right.elements.size) {
                    0
                } else if (i >= left.elements.size) {
                    -1
                } else { // i >= right.elements.size
                    1
                }
            }
        }
    }

    private fun parse(s: String): Node.Sequence {
        val res = mutableListOf<Node>()
        val stack = Stack<Node>()

        var i = 0
        while (i < s.length) {
            when (val c = s[i]) {
                '[' -> stack.push(Node.LEFT)
                ']' -> {
                    val list = mutableListOf<Node>()
                    while (stack.peek() != Node.LEFT) {
                        val node = stack.pop()
                        list.add(node)
                    }
                    stack.pop() // pop '['
                    stack.push(Node.Sequence(list.reversed()))
                }
                ',' -> Unit // ignore
                else -> {
                    var digit = c - '0'
                    while (i < s.length - 1 && s[i + 1].isDigit()) {
                        digit = digit * 10 + (s[i + 1] - '0')
                        i++
                    }
                    stack.push(Node.Digit(digit))
                }
            }
            i++
        }

        while (stack.isNotEmpty()) {
            res.add(stack.pop())
        }
        return Node.Sequence(res.reversed())
    }

    fun part1(inputs: String): Int {
        return inputs.split("\n\n")
            .withIndex()
            .filter { (_, s) ->
                val (a, b) = s.split("\n")
                val left = parse(a)
                val right = parse(b)
                left < right
            }.sumOf { it.index + 1 }
    }

    fun part2(inputs: String): Int {
        val list = inputs.split("\n")
            .filter { it.isNotEmpty() }
            .map { parse(it) }
            .toMutableList()

        val two = Node.Sequence(listOf(Node.Sequence(listOf(Node.Digit(2)))))
        list.add(two)

        val six = Node.Sequence(listOf(Node.Sequence(listOf(Node.Digit(6)))))
        list.add(six)

        list.sortWith { a, b -> a.compareTo(b) }

        val v1 = list.indexOf(two)
        val v2 = list.indexOf(six)
        return (v1 + 1) * (v2 + 1)
    }
}

fun main() {

    fun part1(inputs: String): Int {
        return Day13.part1(inputs)
    }

    fun part2(inputs: String): Int {
        return Day13.part2(inputs)
    }

    val testInput = readText("../data/Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readText("../data/Day13")
    println(part1(input))
    println(part2(input))
}
