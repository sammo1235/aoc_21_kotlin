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

fun data(day: Int, intafy: Boolean = false): {
    var file = File("assets/day_$day.txt").useLines { it.toList() }
    if (intafy) {
        file = file.map { it.toInt() }
    }
    return file
}


class Day1 {
    fun partOne(): Int {
        val data = data(day = 1)
        var result = 0;
        for (i in 1 until data.size) {
            if (data[i-1] < data[i]) {
                result+=1;
            }
        }
        return result;
    }

    fun partTwo(): Int {
        val data = data(day = 1)
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
            println(dataIterator.next())
            val line = dataIterator.next().split("\\s".toRegex()).toTypedArray().toContentString()
            val dir = line[0];
            val qu = line[1];
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
}