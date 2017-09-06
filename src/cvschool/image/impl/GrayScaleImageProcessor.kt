package cvschool.image.impl

import cvschool.image.ImageProcessor
import cvschool.image.util.ImageUtil
import java.awt.image.BufferedImage

class GrayScaleImageProcessor : ImageProcessor {

	override fun processImage(img: BufferedImage): BufferedImage {
		return ImageUtil.filter(img) { x, y, rgb -> toGray(rgb) }
	}

	private fun toGray(rgb: Int): Int {
		val r = rgb shr 16 and 0xff
		val g = rgb shr 8 and 0xff
		val b = rgb and 0xff

		val gray = (0.299 * r + 0.587 * g + 0.114 * b).toInt()

		return (rgb and 0xff000000.toInt()) + (gray shl 16) + (gray shl 8) + gray
	}

}
