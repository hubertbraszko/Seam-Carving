package seamcarving.imageProcessing

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import kotlin.collections.ArrayList

typealias Seam = List<Point>

data class Point(val x : Int, val y : Int)

class SeamFinder(private val energyMatrix : BufferedImage) {

    private val graph = imageToGraph(energyMatrix)


    fun findVerticalSeam() : Seam {
        var source = findSmallestFromArray(getUpperRow())
        val target = findSmallestFromArray(getLowerRow())
        findAllDistances(Point(source.x,source.y))
        val path = getShortestPath(Point(target.x,target.y))
        return toSeam(path);
    }

    private fun findSmallestFromArray(row : List<Node>) : Node {
        var smallest : Node = row[0]
        for(node in row) {
            if(node.value < smallest.value) smallest = node
        }

        return smallest
    }

    private fun getUpperRow() : List<Node> {
        val nodes = ArrayList<Node>()
        for(x in 0 until energyMatrix.width) {
            nodes.add(graph[x][0])
        }
        return nodes
    }

    private fun getLowerRow() : List<Node> {
        val nodes = ArrayList<Node>()
        for(x in 0 until energyMatrix.width) {
            nodes.add(graph[x][energyMatrix.height-1])
        }
        return nodes
    }

    private fun toSeam(nodes : List<Node>) : Seam {
        return nodes.map{Point(it.x,it.y)}
    }

    private fun getShortestPath(target : Point) : List<Node> {

        val nodes = ArrayList<Node>()

        var node : Node? = graph[target.x][target.y]

        while (node != null) {

            nodes.add(node)
            node = node.previous

        }

        return nodes

    }


    private fun findAllDistances(source : Point) {
        val reachable = PriorityQueue<Node>(compareBy {it.distance})
        var node : Node? = graph[source.x][source.y]
        node?.distance = node!!.value

        while(node != null) {
            val children = getChildren(node)
            children.forEach {
                val distance = node!!.distance + it.value
                if(it.distance > distance) {
                    it.distance = distance
                    it.previous = node
                }
            }

            reachable.addAll(children.filter { !it.isProcessed && !reachable.contains(it) })
            node.isProcessed = true
            node = reachable.poll()
        }
    }


    private fun getChildren(node: Node) : List<Node> {
        val children = ArrayList<Node>()

        addNodeToListIfExists(node.x - 1, node.y + 1, children)
        addNodeToListIfExists(node.x, node.y +1, children)
        addNodeToListIfExists(node.x + 1, node.y + 1, children)

        return children
    }


    private fun addNodeToListIfExists(x : Int, y : Int, list : MutableList<Node>) {
        if(x in 0 until energyMatrix.width && y in 0 until energyMatrix.height) {
            list.add(graph[x][y])
        }
    }

    private fun imageToGraph(energyMatrix: BufferedImage) : List<List<Node>> {

        val graph = ArrayList<MutableList<Node>>(energyMatrix.width)

        for(x in 0 until energyMatrix.width) {
            val row = ArrayList<Node>(energyMatrix.height)
            for(y in 0 until energyMatrix.height) {
                row.add(Node(x,y, Color(energyMatrix.getRGB(x,y)).red))
            }
            graph.add(row)
        }

        return graph
    }

}

private data class Node(
    val x : Int,
    val y : Int,
    var value : Int,
    var isProcessed : Boolean = false,
    var previous : Node? = null,
    var distance : Int = Int.MAX_VALUE
)