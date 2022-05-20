package rafalwojcik.prm.Service

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import java.io.File

class FileService {

    companion object{

        fun getBitmapFromFile(filePath: String): Bitmap {
            var source = ImageDecoder.createSource(File(filePath))
            return ImageDecoder.decodeBitmap(source)
        }
    }
}