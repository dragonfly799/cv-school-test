package cvschool.image.impl

import cvschool.image.ImageProcessor
import cvschool.image.util.ImageUtil
import java.awt.image.BufferedImage

class FlipImageProcessor : ImageProcessor {

	override fun processImage(img: BufferedImage): BufferedImage {
		return ImageUtil.filter(img) { x, y, rgb -> img.getRGB(img.width - x - 1, y) }
	}

}
