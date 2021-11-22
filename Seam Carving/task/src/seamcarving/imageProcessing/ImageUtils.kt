package seamcarving.imageProcessing

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.nio.Buffer
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

        fun drawSeam(image: BufferedImage, seam : Seam) {
            seam.forEach { point -> image.setRGB(point.x,point.y, Color.RED.rgb)}
        }

        fun transpose(image : BufferedImage) : BufferedImage {
            var transposed = BufferedImage(image.height,image.width,image.type)

            for(x in 0 until image.width) {
                for(y in 0 until image.height) {
                    transposed.setRGB(y, x, image.getRGB(x, y))
                }
            }

            return transposed
        }

        fun removeSeam(image : BufferedImage, seam : Seam) : BufferedImage {
            var resized = BufferedImage(image.width-1, image.height, image.type)
            var offset = 0;
            for(y in 0 until image.height) {
                for(x in 0 until image.width - 1) {
                    if(seam.contains(Point(x,y))) offset++
                    resized.setRGB(x, y, image.getRGB(x + offset, y))
                }
                offset = 0
            }
            return resized
        }

    }

}