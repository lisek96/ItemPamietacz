package rafalwojcik.prm.service

import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import rafalwojcik.prm.activity.MainActivity

class GalleryService {

    companion object{
        fun getImageList(parentActivity : MainActivity) : List<Bitmap>{
            val imageList = mutableListOf<Bitmap>()
            parentActivity.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.ImageColumns._ID),
                null,
                null,
                null
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                while(cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    var decoder = ImageDecoder.createSource(parentActivity.contentResolver, contentUri)
                    val bitmap = ImageDecoder.decodeBitmap(decoder)
                    imageList += bitmap
                }
            }
            return imageList
        }
    }
}