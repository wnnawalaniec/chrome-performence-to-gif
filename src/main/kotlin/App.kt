import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.JsonReader
import shine.htetaung.giffer.Giffer
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageOutputStream
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("GifMaker in progress!")

    val path = args.getOrElse(0, {"test.json"})
    val file = SourceFile(path)

    with(BasicSourceFileValidator(file)) {
        println("Starting validating $file")
        this.validate()
    }

    println("Creating json reader for file")

    val reader = fileToJsonReader(file)

    reader.beginArray()

    println("Parsing file")

    val screenShotsEntries = getAllScreenshotEntries(reader)

    println("Converting base64 strings to BufferedImages")

    val screenShotsImages = convertEntriesToBufferedImageArray(screenShotsEntries)

    println("Writing images to gif file - result.gif")

    makeGifFileFromBufferedImageArray(screenShotsImages, "result.gif")

    println("All done. Thanks.")
}

fun fileToJsonReader(file: File): JsonReader {
    return JsonReader(fileToInputStreamReader(file))
}

fun fileToInputStreamReader(file: File): InputStreamReader {
    with(FileInputStream(file)) {
        return InputStreamReader(this)
    }
}

fun getAllScreenshotEntries(reader: JsonReader): ArrayList<Entry> {
    val screenShotsEntries = ArrayList<Entry>()

    while (reader.hasNext()) {
        try {
            val entry = Gson().fromJson<Entry>(reader, Entry::class.java)
            if (entry.name == "Screenshot") {
                screenShotsEntries += entry
            }
        } catch (e: JsonSyntaxException) {
            continue
        }
    }

    return screenShotsEntries
}

fun convertEntriesToBufferedImageArray(entries: ArrayList<Entry>): ArrayList<BufferedImage> {
    val screenShotsImages = ArrayList<BufferedImage>()
    with(entries.iterator()) {
        while (this.hasNext()) {
            val entry = this.next()
            val img = decodeBase64ImageToBufferedImage(entry.getBase64Image())
            screenShotsImages += img!!
        }
    }

    return screenShotsImages
}

fun decodeBase64ImageToBufferedImage(base64Image: String): BufferedImage? {
    val decoder = Base64.getDecoder();
    val imageBytes = decoder.decode(base64Image)
    val bis = ByteArrayInputStream(imageBytes)
    bis.close()
    return ImageIO.read(bis)
}

fun makeGifFileFromBufferedImageArray(images: ArrayList<BufferedImage>, fileName: String) {
    Giffer.generateFromBI(images.toTypedArray(), fileName, 10, true)
}

data class Entry(val name: String = "", val args: EntryArgs = EntryArgs()) {
    fun getBase64Image(): String {return this.args.snapshot}
}
data class EntryArgs(val snapshot: String = "")