package seamcarving


import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val width : Int
    val height : Int

    println("Enter rectangle width:")
    width = readLine()!!.toInt()
    println("Enter rectangle height:")
    height = readLine()!!.toInt()

    println("Enter output image name:")
    val name = readLine()!!

    val imageFile = File(name)

    val image = BufferedImage(width,height, BufferedImage.TYPE_INT_RGB)

    val graphics = image.createGraphics()

    graphics.color = Color.RED
    graphics.drawLine(0,0,width,height)
    graphics.drawLine(0,height, width, 0)


    saveImage(image,imageFile)
}

fun saveImage(image: BufferedImage, imageFile: File) {
    ImageIO.write(image, "png", imageFile)
}