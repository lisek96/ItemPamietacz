package rafalwojcik.prm.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.GalleryItemBinding
import rafalwojcik.prm.service.FileService
import rafalwojcik.prm.service.GalleryService
import java.io.File

class GalleryAdapter(
    var onPhotoClick: (File)->Unit,
    _context: Context) : RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    var context = _context
    companion object {
        private var images :List<Bitmap>? = null
    }

    init{
        if(images == null){
            images = GalleryService.getImageList(context as MainActivity)
        }
    }

    class ImageViewHolder(
        private val binding : GalleryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var bitmap : Bitmap

            fun bind(_bitMap: Bitmap){
                bitmap = _bitMap
                binding.imageView.setImageBitmap(_bitMap)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val galleryItemBinding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(galleryItemBinding).apply {
            galleryItemBinding.imageView.setOnClickListener {
                var file = FileService.createFileFromBitmap(this.bitmap, context, null)
                onPhotoClick(file)
            }
        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images!![position])
    }

    override fun getItemCount(): Int {
        return images!!.size
    }
}