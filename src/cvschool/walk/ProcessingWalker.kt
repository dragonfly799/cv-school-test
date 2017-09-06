package cvschool.walk

import cvschool.image.ImageProcessor
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

object ProcessingWalker {

	fun walk(sourceDir: Path, resultDir: Path, imageProcessor: ImageProcessor, ending: String) {
		Files.list(sourceDir).forEach { source ->
			val img = ImageIO.read(source.toFile())
			val res = imageProcessor.processImage(img)
			save(res, resultDir.resolve(getFileName(source, ending)))
		}
	}


	private fun getFileName(source: Path, ending: String): String {
		val fileName = source.fileName.toString()

		val fileNameWithoutExt = fileName.substring(0, fileName.indexOf(".png"))
		return fileNameWithoutExt + "_" + ending + ".png"
	}

	private fun save(img: BufferedImage, file: Path) {
		ImageIO.write(img, "png", file.toFile())
	}
}