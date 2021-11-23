package seamcarving


import seamcarving.imageProcessing.ProcessedImage
import seamcarving.imageProcessing.ImageUtils
import seamcarving.imageProcessing.SeamFinder
import java.awt.image.BufferedImage


fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]
    val deltaWidth : Int = args[5].toInt()
    val deltaHeight : Int = args[7].toInt()


    var image = ImageUtils.getImageFromFile(inputName) // Raw Image

    println("${image.width}  ${image.height}")

    repeat (deltaWidth) {
        image = downsizeByOne(image)
        println("${it+1} / $deltaWidth")
    }

    image = ImageUtils.transpose(image)
    repeat(deltaHeight) {
        image = downsizeByOne(image)
        println("${it+1} / $deltaWidth")
    }
    image = ImageUtils.transpose(image)

    println("${image.width}  ${image.height}")

    ImageUtils.saveImage(image,outputName)


}

fun downsizeByOne(image: BufferedImage): BufferedImage {
    val processedImage = ProcessedImage(image)
    val seam = SeamFinder(processedImage.energyImage, processedImage.energyMatrix).findVerticalSeam()

    return ImageUtils.removeSeam(image, seam)
}
