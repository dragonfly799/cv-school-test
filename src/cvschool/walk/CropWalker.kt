package cvschool.walk

import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Integer.parseInt
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import javax.imageio.ImageIO

object CropWalker {

	fun walk(sourceDir: Path, resultDir: Path, annotations: Path) {
		Files.list(sourceDir).forEach { it ->
			process(it, resultDir, annotations)
		}
	}

	private fun process(source: Path, resultDir: Path, annotations: Path) {
		val img = ImageIO.read(source.toFile())
		val fileName = source.fileName.toString()
		val fileNameWithoutExt = fileName.substring(0, fileName.indexOf(".png"))

		val annotationPath = annotations.resolve(fileNameWithoutExt + ".txt")

		val reader = BufferedReader(InputStreamReader(FileInputStream(annotationPath.toFile())))

		val lines = reader.lines()
				.filter { line -> !line.isNullOrBlank() }
				.collect(Collectors.toList())

		lines.forEachIndexed { i, line ->
			val crop = parseLineAndCrop(line, img)
			val resultPath = resultDir.resolve(fileNameWithoutExt + "_" + i + ".png")
			save(resultPath, crop)
		}
	}

	private fun parseLineAndCrop(line: String, img: BufferedImage): BufferedImage {
		val split = line.split(',')

		val x1 = parseInt(split[0])
		val y1 = parseInt(split[1])
		val x2 = parseInt(split[2])
		val y2 = parseInt(split[3])

		return img.getSubimage(x1, y1, x2 - x1, y2 - y1)
	}

	private fun save(path: Path, crop: BufferedImage) {
		ImageIO.write(crop, "png", path.toFile())
	}
}