package seamcarving


import seamcarving.imageProcessing.ProcessedImage
import seamcarving.imageProcessing.ImageUtils
import seamcarving.imageProcessing.SeamFinder
import java.awt.image.BufferedImage


fun main(args: Array<String>) {

    val (inputName, outputName, deltaWidth, deltaHeight) = parseArgs(args)

    var image = ImageUtils.getImageFromFile(inputName) // Raw Image

    println("REDUCING WIDTH:")
    //downsize to image.width - deltaWidth
    repeat (deltaWidth) {
        image = downsizeByOne(image)
        println("${it+1} / $deltaWidth")
    }

    println("REDUCING HEIGHT:")
    //downsize to image.height - deltaHeight
    image = ImageUtils.transpose(image)
    repeat(deltaHeight) {
        image = downsizeByOne(image)
        println("${it+1} / $deltaHeight")
    }
    image = ImageUtils.transpose(image)

    ImageUtils.saveImage(image,outputName)
    println("---Image resized successfully---")

}

fun downsizeByOne(image: BufferedImage): BufferedImage {
    val processedImage = ProcessedImage(image)
    val seam = SeamFinder(processedImage.energyImage, processedImage.energyMatrix).findVerticalSeam()

    return ImageUtils.removeSeam(image, seam)
}

fun parseArgs(args: Array<String>): Arguments {
    if (args.size !in listOf(2, 4, 6, 8)) {
        throw Exception("usage: -in <input image> -out <output image> -width <width> -height <height>")
    }

    var inputFilename = ""
    var outputFilename = ""
    var deltaWidth = 0
    var deltaHeight = 0

    for (arg in args.asList().chunked(2)) {
        val paramKey = arg[0]
        val paramValue = arg[1]

        when (paramKey) {
            "-in" -> inputFilename = paramValue
            "-out" -> outputFilename = paramValue
            "-width" -> deltaWidth = paramValue.toInt()
            "-height" -> deltaHeight = paramValue.toInt()
        }
    }

    return Arguments(inputFilename, outputFilename, deltaWidth, deltaHeight)
}

data class Arguments(
    val inputFilename: String,
    val outputFilename: String,
    val deltaWidth: Int,
    val deltaHeight: Int
)