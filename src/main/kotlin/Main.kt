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
        var transposed = transposeArray(data)

        var gamma: String = ""
        var epsilon: String = ""
        for (i in 0 until column) {
            var transposedLine = transposed[i]
            val tally = transposed[i].toCollection(ArrayList()).groupingBy { it }.eachCount().toList().sortedBy { (_, value) -> value }
            gamma = gamma.plus(tally[1].first.toString())
            epsilon = epsilon.plus(tally[0].first.toString())
        }
        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun partTwo(): Int {
        var data = data(3)
        val oxy = calculate(data, "oxygen", '1')
        val co2 = calculate(data, "co2", '0')
        return oxy.toInt(2) * co2.toInt(2);
    }

    fun calculate(data: List<String>, method: String, precedence: Char): String {
        var dat = data
        while(dat.size > 1) {
            loop@ for (i in dat.indices) {
                var transposed = transposeArray(dat)
                var transposedLine = transposed[i]
                val tally = transposed[i].toCollection(ArrayList()).groupingBy { it }.eachCount().toList().sortedBy { (_, value) -> value }
                dat = if (tally[0].second == tally[1].second) {
                    dat.filter { it[i] == precedence }
                } else {
                    if (method == "oxygen") { // most occurrences stay
                        dat.filter { it[i].digitToInt() == tally[1].first }
                    } else {                  // least occurrences stay
                        dat.filter { it[i].digitToInt() == tally[0].first }
                    }
                }
                if (dat.size == 1) {
                    break@loop
                }
            }
        }
        return dat[0]
    }

    fun transposeArray(array: List<String>): Array<IntArray> {
        val row = array.size
        val column = array[0].length
        var transpose = Array(column) { IntArray(row) }
        for (i in 0 until row) {
            for (j in 0 until column) {
                transpose[j][i] = array[i].toCharArray()[j].digitToInt()
            }
        }
        return transpose;
    }
}
