package seamcarving.imageProcessing

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ImageUtils {


    companion object {
        fun saveImage(image: BufferedImage, fileName: String) {
            val imageFile = File(fileName)
            ImageIO.write(image, "png", imageFile)
        }

        fun getImageFromFile(fileName: String): BufferedImage {
            val file = File(fileName)
            return ImageIO.read(file);
        }
    }

}