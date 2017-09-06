package cvschool.image.util

import java.awt.Image
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.awt.image.FilteredImageSource
import java.awt.image.RGBImageFilter

object ImageUtil {

	fun filter(img: BufferedImage, filterAction: (x: Int, y: Int, rgb: Int) -> Int): BufferedImage {
		val filter = object : RGBImageFilter() {
			override fun filterRGB(x: Int, y: Int, rgb: Int): Int {
				return filterAction(x, y, rgb)
			}
		}

		val producer = FilteredImageSource(img.source, filter)
		return toBufferedImage(Toolkit.getDefaultToolkit().createImage(producer))
	}

	private fun toBufferedImage(image: Image): BufferedImage {
		val dest = BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB)

		val graphics = dest.createGraphics()
		graphics.drawImage(image, 0, 0, null)
		graphics.dispose()

		return dest
	}
}
