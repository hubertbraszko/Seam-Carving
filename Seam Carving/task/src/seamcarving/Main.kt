package seamcarving


import seamcarving.imageProcessing.ImageProcessor
import seamcarving.imageProcessing.ImageUtils


fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]

    val image = ImageUtils.getImageFromFile(inputName)

    val imageProcessor = ImageProcessor(image)

    val processedImage = imageProcessor.getImageOfPixelEnergy()

    ImageUtils.saveImage(processedImage,outputName)

}

