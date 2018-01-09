import java.util.ArrayList
import java.util.HashMap

class ShortestWeightedPath() {

    val infinity = 1000000
    var heapArray = IntArray(0)
    var nodeParentMap = HashMap<Int, Int>()
    var distanceMap = HashMap<Int, Int>()

    fun shortestWeightedPath(graph: HashMap<Int, ArrayList<Pair<Int, Int>>>): Pair<HashMap<Int, Int>, HashMap<Int, Int>> {
        initializeHeapArray(graph)
        while (heapArray.isNotEmpty()) {
            var extractedNode = heapArray[0]
            calculateShortestPathsAndParents(graph, extractedNode)
            balanceHeap()
        }
        return Pair(distanceMap, nodeParentMap)
    }

    private fun initializeHeapArray(graph: HashMap<Int, ArrayList<Pair<Int, Int>>>) {
        heapArray = heapArray.copyOf(graph.size)
        for (n in 0..graph.size - 1) {
            heapArray[n] = n + 1
            if (n == 0) {
                distanceMap.put(n + 1, 0)
            } else {
                distanceMap.put(n + 1, infinity)
            }
        }
    }

    private fun calculateShortestPathsAndParents(graph: HashMap<Int, ArrayList<Pair<Int, Int>>>, extractedNode: Int) {
        for (edge in graph.get(extractedNode) ?: arrayListOf()) {
            if (heapArray.contains(edge.first)) {
                var edgeDistance = edge.second + (distanceMap.get(extractedNode) ?: 0)
                if (edgeDistance < distanceMap.get(edge.first) ?: -1) {
                    distanceMap.put(edge.first, edgeDistance)
                    nodeParentMap.put(edge.first, extractedNode)
                }
            }
        }
    }

    private fun balanceHeap() {
        heapArray[0] = heapArray[heapArray.size - 1]
        heapArray = heapArray.dropLast(1).toIntArray()
        if (heapArray.isEmpty()) return
        heapifyDown()
    }

    private fun heapifyDown() {
        var currentIndex = 0
        var leftChildIndex = getLeftChildIndex(0)
        var rightChildIndex = getRightChildIndex(0)

        while (leftChildIndex != -1) {
            var parentDistance = distanceMap.get(heapArray[0])
            var leftChild = getNode(leftChildIndex)
            var leftChildDistance = distanceMap.get(leftChild)
            var rightChild = getNode(rightChildIndex)
            var rightChildDistance = distanceMap.get(rightChild)
            var minChildDistance = leftChildDistance
            var minChildIndex = leftChildIndex
            if (rightChildIndex != -1 && rightChildDistance ?: -1 < leftChildDistance ?: -1) {
                minChildDistance = rightChildDistance
                minChildIndex = rightChildIndex
            }
            if (minChildDistance ?: -1 < parentDistance ?: -1) {
                swap(0, minChildIndex)
            }
            var currentNode = currentIndex++
            leftChildIndex = getLeftChildIndex(currentNode)
            rightChildIndex = getRightChildIndex(currentNode)
        }
    }

    private fun getNode(childIndex: Int): Int {
        if (childIndex < heapArray.size && childIndex > 0) {
            return heapArray[childIndex]
        } else {
            return -1
        }
    }

    private fun swap(toSwapIndex: Int, swapWithIndex: Int) {
        var toSwapValue = heapArray[toSwapIndex]
        heapArray[toSwapIndex] = heapArray[swapWithIndex]
        heapArray[swapWithIndex] = toSwapValue
    }

    private fun getParentIndex(index: Int): Int? {
        if (index < heapArray.size) {
            var currentIndex = heapArray[index]
            var parentIndex = heapArray[((index - 1) / 2)]
            if (currentIndex == parentIndex) {
                return null
            } else {
                return parentIndex
            }
        } else {
            return null
        }
    }

    private fun getLeftChildIndex(index: Int): Int {
        var leftChildIndex = (index * 2) + 1
        if (((index * 2) + 1) < heapArray.size) {
            return leftChildIndex
        } else {
            return -1
        }
    }

    private fun getRightChildIndex(index: Int): Int {
        var rightChildIndex = (index * 2) + 2
        if (((index * 2) + 2) < heapArray.size) {
            return rightChildIndex
        } else {
            return -1
        }
    }
}