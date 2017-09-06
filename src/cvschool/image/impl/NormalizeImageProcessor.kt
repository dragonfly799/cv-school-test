package cvschool.image.impl

import cvschool.image.ImageProcessor
import cvschool.image.util.ImageUtil
import java.awt.image.BufferedImage

class NormalizeImageProcessor : ImageProcessor {

	override fun processImage(img: BufferedImage): BufferedImage {

		val rgbRange = RGBRange()
		for (x in 0..img.width - 1) {
			for (y in 0..img.height - 1) {
				val rgb = img.getRGB(x, y)

				rgbRange.r.update(rgb shr 16 and 0xff)
				rgbRange.g.update(rgb shr 8 and 0xff)
				rgbRange.b.update(rgb and 0xff)
			}
		}

		return ImageUtil.filter(img) { x, y, rgb ->
			normalize(rgbRange, rgb)
		}
	}

	private fun normalize(rgbRange: RGBRange, rgb: Int): Int {
		val r = rgbRange.r.normalize(rgb shr 16 and 0xff)
		val g = rgbRange.g.normalize(rgb shr 8 and 0xff)
		val b = rgbRange.b.normalize(rgb and 0xff)

		return (rgb and 0xff000000.toInt()) + (r shl 16) + (g shl 8) + b
	}

	private class RGBRange {
		internal var r = ColorRange()
		internal var g = ColorRange()
		internal var b = ColorRange()
	}

	private class ColorRange {
		internal var min = 255
		internal var max = 0

		internal fun update(value: Int) {
			if (value < min) {
				min = value
			}
			if (value > max) {
				max = value
			}
		}

		internal fun normalize(value: Int): Int {
			return (value - min) * 255 / (max - min)
		}
	}
}

