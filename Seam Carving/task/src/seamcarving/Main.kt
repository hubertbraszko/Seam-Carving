package seamcarving


import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]

    val inputFile = File(inputName)

    val image : BufferedImage = ImageIO.read(inputFile)

    for (x in 0 until image.width) {
        for (y in 0 until image.height) {

            val color = Color(image.getRGB(x, y))

            val r = 255 - color.red
            val g = 255 - color.green
            val b = 255 - color.blue

            val reversedColor = Color(r,g,b)

            image.setRGB(x,y,reversedColor.rgb)

        }
    }

    val outputFile = File(outputName)
    saveImage(image,outputFile)
}

fun saveImage(image: BufferedImage, imageFile: File) {
    ImageIO.write(image, "png", imageFile)
}