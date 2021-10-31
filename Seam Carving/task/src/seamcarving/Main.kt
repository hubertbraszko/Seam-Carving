package seamcarving


import seamcarving.imageProcessing.ImageUtils
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]

    val image = ImageUtils.getImageFromFile(inputName)


}

