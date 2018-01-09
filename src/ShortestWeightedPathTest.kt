import java.io.File
import java.util.ArrayList
import java.util.HashMap

object ShortestWeightedPathTest {
    @JvmStatic
    fun main(args: Array<String>) {
        buildGraphFromDataSet()
    }

    private fun buildGraphFromDataSet() {
        var graph = HashMap<Int, ArrayList<Pair<Int, Int>>>()
        File("src/data.txt")
                .inputStream()
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach {
                        var mKey = 0
                        var keys = Regex("^[^\\,]+\\s").findAll(it)
                        for (key in keys) {
                            mKey = key.value.trim().toInt()
                            graph.put(mKey, arrayListOf())
                        }
                        var pairs = Regex("\\s.+").findAll(it)
                        for (pairValueArray in pairs) {
                            var pairValueArray = pairValueArray.value.trim().split("\\s".toRegex())
                            for (pair in pairValueArray) {
                                var pairList = pair.split(",")
                                graph.get(mKey)?.add(Pair(pairList[0].toInt(), pairList[1].toInt()))
                            }
                        }
                    }
                }
        test(graph)
    }

    private fun test(graph: HashMap<Int, ArrayList<Pair<Int, Int>>>) {
        var result = ShortestWeightedPath().shortestWeightedPath(graph)
        println("Distance Map: " + result.first)
        println("Parent Map: " + result.second)
        println(result.first.get(7).toString()+","+
                result.first.get(37).toString()+","+
                result.first.get(59).toString()+","+
                result.first.get(82).toString()+","+
                result.first.get(99).toString()+","+
                result.first.get(115).toString()+","+
                result.first.get(133).toString()+","+
                result.first.get(165).toString()+","+
                result.first.get(188).toString()+","+
                result.first.get(197).toString()
        )
    }
}