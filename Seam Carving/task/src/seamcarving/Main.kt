package seamcarving


import seamcarving.imageProcessing.ImageProcessor
import seamcarving.imageProcessing.ImageUtils
import seamcarving.imageProcessing.SeamFinder


fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]


    val image = ImageUtils.getImageFromFile(inputName)

    val imageProcessor = ImageProcessor(image)
    val energyImage = imageProcessor.getImageOfPixelEnergy()

    val seamFinder = SeamFinder(energyImage)
    val seam = seamFinder.findVerticalSeam()


    val output = ImageUtils.getImageFromFile(inputName)
    ImageUtils.drawSeam(output,seam)

    ImageUtils.saveImage(output,outputName)

}



