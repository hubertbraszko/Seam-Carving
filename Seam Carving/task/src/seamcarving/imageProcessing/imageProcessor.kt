package seamcarving.imageProcessing

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.*

class imageProcessor(private val image: BufferedImage) {


    fun calculateEnergyOfEachPixel() {


        val energyMatrix = emptyList<MutableList<Double>>()


        for(x in 0 until image.width) {
            for(y in 0 until image.height) {

                energyMatrix[]






            }
        }

    }


    fun getEnergyOfPixel(x : Int, y : Int) : Double {

        var trueX = x
        var trueY = y

        if(x == 0) trueX += 1
        if(x == image.width - 1) trueX -= 1
        if(y == 0) trueY += 1
        if(y == image.height - 1) trueY -= 1

        return sqrt(getSquareOfGradientX(trueX, trueY)+getSquareOfGradientY(trueX,trueY))
    }

    private fun getSquareOfGradientX(x : Int, y : Int) : Double {
        return getXDifferencesFor("Red",x,y).pow(2) +
                getXDifferencesFor("Green",x,y).pow(2) +
                getXDifferencesFor("Blue",x,y).pow(2)
    }

    private fun getSquareOfGradientY(x : Int, y : Int) : Double {
        return getYDifferencesFor("Red",x,y).pow(2) +
                getYDifferencesFor("Green",x,y).pow(2) +
                getYDifferencesFor("Blue",x,y).pow(2)
    }

    private fun getXDifferencesFor(color : String, x : Int, y : Int) : Double {

        val leftRgb = Color(image.getRGB(x-1,y))
        val rightRgb = Color(image.getRGB(x+1,y))

        val result = when (color) {
            "red" -> leftRgb.red - rightRgb.red
            "green" -> leftRgb.green-rightRgb.green
            "blue" -> leftRgb.blue - rightRgb.blue
            else -> 0
        }

        return result.toDouble()

    }

    private fun getYDifferencesFor(color : String, x : Int, y : Int) : Double {

        val upperRgb = Color(image.getRGB(x,y-1))
        val lowerRgb = Color(image.getRGB(x,y+1))

        val result = when (color) {
            "red" -> upperRgb.red - lowerRgb.red
            "green" -> upperRgb.green-lowerRgb.green
            "blue" -> upperRgb.blue - lowerRgb.blue
            else -> 0
        }

        return result.toDouble()
    }

}