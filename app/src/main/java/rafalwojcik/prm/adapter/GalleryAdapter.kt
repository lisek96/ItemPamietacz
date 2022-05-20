package rafalwojcik.prm.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.GalleryItemBinding
import rafalwojcik.prm.service.GalleryService

class GalleryAdapter(_context: Context) : RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    var context = _context
    companion object {
        private var images :List<Bitmap>? = null
    }

    init{
        if(images == null){
            images = GalleryService.getImageList(context as MainActivity)
            println(images)
        }
    }

    class ImageViewHolder(
        private val binding : GalleryItemBinding) : RecyclerView.ViewHolder(binding.root){

            fun bind(bitMap: Bitmap){
                binding.imageView.setImageBitmap(bitMap)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val galleryItemBinding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(galleryItemBinding).also {
            galleryItemBinding.root.setOnClickListener {
                println("placeholder")
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