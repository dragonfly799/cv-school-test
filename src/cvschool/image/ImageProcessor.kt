package cvschool.image

import java.awt.image.BufferedImage

interface ImageProcessor {

	fun processImage(img: BufferedImage): BufferedImage

}

