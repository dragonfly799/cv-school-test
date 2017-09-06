package cvschool

import cvschool.image.impl.FlipImageProcessor
import cvschool.image.impl.GrayScaleImageProcessor
import cvschool.image.impl.NoiseImageProcessor
import cvschool.image.impl.NormalizeImageProcessor
import cvschool.walk.CropWalker
import cvschool.walk.ProcessingWalker
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

private val DEFAULT_RESOURCE_PATH = "./"

fun main(args: Array<String>) {

	val root = if (args.size > 0) args[0] else DEFAULT_RESOURCE_PATH
	val rootPath = Paths.get(root)

	val images = rootPath.resolve("images")

	val fragments = rootPath.resolve("fragments")
	val fragmentsGrayscale = rootPath.resolve("fragments_greyscale")
	val fragmentsFlip = rootPath.resolve("fragments_flip")
	val fragmentsNormalize = rootPath.resolve("fragments_normalize")
	val fragmentsNoise = rootPath.resolve("fragments_noise")

	recreateDirs(fragments, fragmentsGrayscale, fragmentsFlip, fragmentsNormalize, fragmentsNoise)

	crop(images, fragments, rootPath.resolve("annotations"))

	toGreyScale(fragments, fragmentsGrayscale)

	flip(fragments, fragmentsFlip)

	normalize(fragmentsFlip, fragmentsNormalize)

	addNoise(fragmentsGrayscale, fragmentsNoise)
}

private fun recreateDirs(vararg dirs: Path) {
	for (dir in dirs) {
		if (Files.exists(dir)) {
			val files = Files.list(dir).collect(Collectors.toList())
			for (file in files) {
				Files.deleteIfExists(file)
			}
			Files.delete(dir)
		}
		Files.createDirectory(dir)
	}
}

private fun crop(source: Path, dest: Path, annotations: Path) {
	CropWalker.walk(source, dest, annotations)
}

private fun toGreyScale(source: Path, dest: Path) {
	ProcessingWalker.walk(source, dest, GrayScaleImageProcessor(), "grey")
}

private fun flip(source: Path, dest: Path) {
	ProcessingWalker.walk(source, dest, FlipImageProcessor(), "flip")
}

private fun normalize(source: Path, dest: Path) {
	ProcessingWalker.walk(source, dest, NormalizeImageProcessor(), "normalize")
}

private fun addNoise(source: Path, dest: Path) {
	ProcessingWalker.walk(source, dest, NoiseImageProcessor(), "noise")
}
