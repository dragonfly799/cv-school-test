package cvschool.image.impl

import cvschool.image.ImageProcessor
import cvschool.image.util.ImageUtil
import java.awt.image.BufferedImage
import java.util.*

class NoiseImageProcessor : ImageProcessor {
	private val random = Random()
	private val variance = 10

	override fun processImage(img: BufferedImage): BufferedImage {
		return ImageUtil.filter(img) { x, y, rgb -> addRgbNoise(rgb) }
	}

	private fun addRgbNoise(rgb: Int): Int {
		val r = addNoise(rgb shr 16 and 0xff)
		val g = addNoise(rgb shr 8 and 0xff)
		val b = addNoise(rgb and 0xff)

		return (rgb and 0xff000000.toInt()) + (r shl 16) + (g shl 8) + b
	}

	private fun addNoise(color: Int): Int {
		val noise = (random.nextGaussian() * variance).toInt()
		var res = color + noise
		if (res > 255) {
			res = 255
		}
		if (res < 0) {
			res = 0
		}
		return res
	}

}
