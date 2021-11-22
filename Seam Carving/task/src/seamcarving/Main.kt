package seamcarving


import seamcarving.imageProcessing.ImageProcessor
import seamcarving.imageProcessing.ImageUtils
import seamcarving.imageProcessing.SeamFinder


fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]
    //val desiredWidth : Int = args[5].toInt()
    //val desiredHeight : Int = args[7].toInt()


    val image = ImageUtils.getImageFromFile(inputName) // Raw Image

    println("${image.width}  ${image.height}")

  //  val transposedImage = ImageUtils.transpose(image) // Transposed Raw Image

    val imageProcessor = ImageProcessor(image)  // ImageProcessor init
    val energyImage = imageProcessor.getImageOfPixelEnergy() // Image of energy

    val seamFinder = SeamFinder(energyImage, imageProcessor.getEnergyMatrix()) // SeamFinder init
    val seam = seamFinder.findVerticalSeam() // Finding vertical seam


    val img = ImageUtils.getImageFromFile(inputName) // getting Raw Image to paint seam on it

  //  ImageUtils.drawSeam(output,seam) // Drawing seam on original image

    val output = ImageUtils.removeSeam(img,seam)

    println("${output.width}  ${output.height}")

    ImageUtils.saveImage(output,outputName) // save original image with seam painted on it

}



