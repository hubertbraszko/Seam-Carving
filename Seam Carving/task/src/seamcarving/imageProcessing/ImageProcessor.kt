package seamcarving.imageProcessing

import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.*

class ImageProcessor(private val image: BufferedImage) {

    private var maxEnergy : Double = 0.0

    fun getImageOfPixelEnergy() : BufferedImage {

        val energyMatrix = calculateEnergyOfEachPixel()

        for(x in 0 until image.width) {
            for(y in 0 until image.height) {

                var normalizedEnergy = normalizeEnergy(energyMatrix[x][y])
                var intensity = Color(normalizedEnergy,normalizedEnergy,normalizedEnergy)
                image.setRGB(x,y,intensity.rgb)
            }
        }
        return image
    }

    private fun normalizeEnergy(energy : Double) : Int {

        return (255 * energy / maxEnergy).toInt()
    }

    private fun findMaxEnergyValue(energyMatrix : MutableList<MutableList<Double>>) {
        var maxEnergy : Double = 0.0
        for(x in 0 until image.width) {
            for(y in 0 until image.height) {
                if(energyMatrix[x][y] > maxEnergy) maxEnergy = energyMatrix[x][y]
            }
        }

        this.maxEnergy = maxEnergy
    }

    private fun calculateEnergyOfEachPixel() : MutableList<MutableList<Double>> {
        //val energyMatrix = mutableListOf<MutableList<Double>>()
        val energyMatrix = MutableList(image.width) {MutableList(image.height) { 0.0 } }
        for(x in 0 until image.width) {
            //energyMatrix.add(mutableListOf<Double>())
            for(y in 0 until image.height) {
                energyMatrix[x][y] = getEnergyOfPixel(x,y)
            }
        }
        findMaxEnergyValue(energyMatrix)
        return energyMatrix
    }


    private fun getEnergyOfPixel(x : Int, y : Int) : Double {

        var trueX = x
        var trueY = y

        if(x == 0) trueX += 1
        if(x == image.width - 1) trueX -= 1
        if(y == 0) trueY += 1
        if(y == image.height - 1) trueY -= 1

        return sqrt(getSquareOfGradientX(trueX, trueY)+getSquareOfGradientY(trueX,trueY))
    }

    private fun getSquareOfGradientX(x : Int, y : Int) : Double {
        return getXDifferencesFor("red",x,y).pow(2) +
                getXDifferencesFor("green",x,y).pow(2) +
                getXDifferencesFor("blue",x,y).pow(2)
    }

    private fun getSquareOfGradientY(x : Int, y : Int) : Double {
        return getYDifferencesFor("red",x,y).pow(2) +
                getYDifferencesFor("green",x,y).pow(2) +
                getYDifferencesFor("blue",x,y).pow(2)
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