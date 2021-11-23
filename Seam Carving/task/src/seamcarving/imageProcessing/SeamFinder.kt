package seamcarving.imageProcessing

import java.awt.image.BufferedImage
import java.util.*
import kotlin.collections.ArrayList

typealias Seam = List<Point>

data class Point(val x : Int, val y : Int)

class SeamFinder(private val energyMatrix : BufferedImage, private val rawEnergyMatrix: MutableList<MutableList<Double>>) {

    private val graph = imageToGraph(rawEnergyMatrix)


    fun findVerticalSeam() : Seam {
        var source = Point(0,0)
        val target = Point(graph.lastIndex, graph[0].lastIndex)
        findAllDistances(Point(source.x,source.y))
        val path = getShortestPath(Point(target.x,target.y))
        return toSeam(path);
    }


    private fun toSeam(nodes : List<Node>) : Seam {
        return nodes.mapNotNull{
            if (it.y == 0 || it.y == graph[0].lastIndex) null
            else Point(it.x,it.y - 1 )
        }
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

        //Add node to the right if the root is in first or list row
        if (node.y == 0 || node.y == graph[0].lastIndex) {
            addNodeToListIfExists(node.x + 1, node.y, children)
        }


        addNodeToListIfExists(node.x - 1, node.y + 1, children)
        addNodeToListIfExists(node.x, node.y +1, children)
        addNodeToListIfExists(node.x + 1, node.y + 1, children)

        return children
    }


    private fun addNodeToListIfExists(x : Int, y : Int, list : MutableList<Node>) {
        if(x in 0..graph.lastIndex && y in 0..graph[0].lastIndex) {
            list.add(graph[x][y])
        }
    }

    private fun imageToGraph(rawEnergyMatrix: MutableList<MutableList<Double>>) : List<List<Node>> {

        val graph = ArrayList<MutableList<Node>>(energyMatrix.width)

        for(x in 0 until energyMatrix.width) {
            val row = ArrayList<Node>(energyMatrix.height)
            for(y in 0 until energyMatrix.height + 2) {
                if(y != 0 && y != energyMatrix.height + 1) {
                    row.add(Node(x, y, rawEnergyMatrix[x][y-1]))
                } else {
                    row.add(Node(x,y, 0.0)) //padding
                }
            }
            graph.add(row)
        }

        return graph
    }

}

private data class Node(
    val x : Int,
    val y : Int,
    var value : Double,
    var isProcessed : Boolean = false,
    var previous : Node? = null,
    var distance : Double = Double.POSITIVE_INFINITY
)