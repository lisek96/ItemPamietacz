package rafalwojcik.prm.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.concurrent.thread

class FileService {

    companion object{

        fun getBitmapFromFile(file: File): Bitmap {
            var source = ImageDecoder.createSource(file)
            return ImageDecoder.decodeBitmap(source)
        }

        fun createFileFromBitmap(bitmap: Bitmap, context: Context, filePath: String?) : File{
                var currentFilePath = filePath ?: getPath(context)
                var file = File(currentFilePath)
                var out = BufferedOutputStream(FileOutputStream(file))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                return file
        }

        fun getPath(context: Context) : String = """${context.filesDir}${UUID.randomUUID()}.jpg"""
    }
}