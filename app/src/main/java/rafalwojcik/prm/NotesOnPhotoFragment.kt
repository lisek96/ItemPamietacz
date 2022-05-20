package rafalwojcik.prm

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafalwojcik.prm.Service.FileService
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding
import java.io.File

class NotesOnPhotoFragment() : Fragment() {
    private lateinit var binding: NotesOnPhotoFragmentBinding
    private lateinit var filePath : String
    private lateinit var bitMap : Bitmap
    private lateinit var parentActivity : MainActivity

    fun prepareBitMap(filePath: String): NotesOnPhotoFragment {
        this.filePath = filePath
        bitMap = FileService.getBitmapFromFile(filePath)
        return this
    }

    fun onCancelPressed(){
        parentActivity.onBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = requireActivity() as MainActivity
        return NotesOnPhotoFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
            binding.paintView.photo = bitMap
            binding.imageButtonCancelPhoto.setOnClickListener { onCancelPressed() }
        }.root
    }
}