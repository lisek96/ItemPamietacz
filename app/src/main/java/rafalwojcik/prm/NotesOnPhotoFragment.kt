package rafalwojcik.prm

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding
import java.io.File

class NotesOnPhotoFragment() : Fragment() {
    private lateinit var binding: NotesOnPhotoFragmentBinding
    private lateinit var filePath : String
    private lateinit var bitMap : Bitmap
    private val mainActivity = requireActivity() as MainActivity

    constructor(_filePath : String) : this() {
        filePath = _filePath
        prepareBitMap(filePath)
        println("BUILDING")
    }

    fun prepareBitMap(filePath: String): NotesOnPhotoFragment {
        this.filePath = filePath
        var source = ImageDecoder.createSource(File(filePath))
        bitMap = ImageDecoder.decodeBitmap(source)
        return this
    }

    fun onCancelPressed(){
        mainActivity.onBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NotesOnPhotoFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
            binding.paintView.photo = bitMap
            binding.imageButtonCancelPhoto.setOnClickListener { onCancelPressed() }
        }.root
    }
}