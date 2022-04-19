import java.io.File
fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

class Sample() {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}

fun data(day: Int): List<String> {
    return File("assets/day_$day.txt").useLines { it.toList() }
}

fun dataAsInts(day: Int): List<Int> {
    return File("assets/day_$day.txt").useLines { it.toList() }.map { it.toInt() }
}


class Day1 {
    fun partOne(): Int {
        val data = dataAsInts(day = 1)
        var result = 0;
        for (i in 1 until data.size) {
            if (data[i-1] < data[i]) {
                result+=1;
            }
        }
        return result;
    }

    fun partTwo(): Int {
        val data = dataAsInts(day = 1)
        var result = 0;
        for (i in 1 until data.size - 2) {
            if (data.slice(i - 1..i + 1).sum() < data.slice(i..i + 2).sum()) {
                result += 1
            }
        }
        return result;
    }
}

class Day2 {
    fun partOne(): Int {
        val data = data(day = 2)
        var x = 0;
        var y = 0;
        val dataIterator = data.iterator();
        while (dataIterator.hasNext()) {
            val line = dataIterator.next().split("\\s".toRegex()).toTypedArray()
            val dir = line[0];
            val qu = line[1].toInt();
            if (dir == "forward") {
                x += qu
            } else if (dir == "down") {
                y += qu
            } else if (dir == "up") {
                y -= qu
            } else {
                x -= qu
            }
        }
        return x*y;
    }

    fun partTwo(): Int {
        val data = data(day = 2)
        var x = 0;
        var y = 0;
        var a = 0;
        val dataIterator = data.iterator();
        while(dataIterator.hasNext()) {
            val line = dataIterator.next().split("\\s".toRegex()).toTypedArray();
            val dir = line[0];
            val qu = line[1].toInt();
            if (dir == "forward") {
                x += qu;
                y += a * qu;
            } else if (dir == "down") {
                a += qu;
            } else if (dir == "up") {
                a -= qu;
            }
        }
        return x * y;
    }
}

class Day3 {
    fun partOne(): Int {
        val data = data(3)
        val row = data.size
        val column = data[0].length
        var transpose = Array(column) { IntArray(row) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                transpose[j][i] = data[i].toCharArray()[j].digitToInt()
            }
        }
        var combine1: String = ""
        var combine2: String = ""
        for (k in 0 until column) {
            val zeros = transpose[k].contentToString().filter { it == '0' }.count()
            val ones = transpose[k].contentToString().filter { it == '1' }.count()
            if (zeros > ones) {
                combine1 = combine1.plus("0")
                combine2 = combine2.plus("1")
            } else {
                combine1 = combine1.plus("1")
                combine2 = combine2.plus("0")
            }
        }
        return combine1.toInt(2) * combine2.toInt(2)
    }
}
