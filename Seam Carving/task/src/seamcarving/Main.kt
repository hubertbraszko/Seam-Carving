package seamcarving


import seamcarving.imageProcessing.ImageProcessor
import seamcarving.imageProcessing.ImageUtils
import seamcarving.imageProcessing.Seam
import seamcarving.imageProcessing.SeamFinder
import java.awt.image.BufferedImage


fun main(args: Array<String>) {

    val inputName : String = args[1]
    val outputName : String = args[3]
    val deltaWidth : Int = args[5].toInt()
    val deltaHeight : Int = args[7].toInt()


    var image = ImageUtils.getImageFromFile(inputName) // Raw Image
    var img = ImageUtils.getImageFromFile(inputName)

    println("${image.width}  ${image.height}")

    var imageProcessor : ImageProcessor
    var energyImage : BufferedImage
    var seamFinder : SeamFinder
    var seam : Seam
    //var img : BufferedImage

    for (i in 0 until deltaWidth) {
        imageProcessor = ImageProcessor(image)
        energyImage = imageProcessor.getImageOfPixelEnergy()
        seamFinder = SeamFinder(energyImage, imageProcessor.getEnergyMatrix())
        seam = seamFinder.findVerticalSeam()
        image = ImageUtils.removeSeam(img,seam)
        ImageUtils.saveImage(image,outputName)
        img = ImageUtils.getImageFromFile(outputName)
        println("${i+1} / $deltaWidth")
    }

    image = ImageUtils.transpose(ImageUtils.getImageFromFile(outputName))
    img = ImageUtils.transpose(ImageUtils.getImageFromFile(outputName))
    for (i in 0 until deltaHeight) {
        imageProcessor = ImageProcessor(image)
        energyImage = imageProcessor.getImageOfPixelEnergy()
        seamFinder = SeamFinder(energyImage, imageProcessor.getEnergyMatrix())
        seam = seamFinder.findVerticalSeam()
        image = ImageUtils.removeSeam(img,seam)
        ImageUtils.saveImage(image,outputName)
        img = ImageUtils.getImageFromFile(outputName)
        println("${i+1} / $deltaHeight")
    }

    image = ImageUtils.transpose(ImageUtils.getImageFromFile(outputName))

  //  val transposedImage = ImageUtils.transpose(image) // Transposed Raw Image

   // val imageProcessor = ImageProcessor(image)  // ImageProcessor init
   // val energyImage = imageProcessor.getImageOfPixelEnergy() // Image of energy

  //  val seamFinder = SeamFinder(energyImage, imageProcessor.getEnergyMatrix()) // SeamFinder init
   // val seam = seamFinder.findVerticalSeam() // Finding vertical seam


   // val img = ImageUtils.getImageFromFile(inputName) // getting Raw Image to paint seam on it

  //  ImageUtils.drawSeam(output,seam) // Drawing seam on original image



    println("${image.width}  ${image.height}")

    ImageUtils.saveImage(image,outputName) // save original image with seam painted on it

}



