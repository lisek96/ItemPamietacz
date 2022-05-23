package rafalwojcik.prm.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import rafalwojcik.prm.service.FileService
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.database.DatabaseGiver
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding
import rafalwojcik.prm.model.Product
import java.io.File
import kotlin.concurrent.thread

class NotesOnPhotoFragment() : Fragment() {
    private lateinit var binding: NotesOnPhotoFragmentBinding
    private lateinit var filePath : String
    private lateinit var originalBitmap : Bitmap
    private lateinit var parentActivity : MainActivity

    fun prepareBitMap(filePath: String): NotesOnPhotoFragment {
        this.filePath = filePath
        originalBitmap = FileService.getBitmapFromFile(File(filePath))
        return this
    }

    fun prepareBitMap(file : File) : NotesOnPhotoFragment{
        this.filePath = file.path
        originalBitmap = FileService.getBitmapFromFile(file)
        return this
    }

    fun onCancelPressed(){
        File(filePath)?.delete()
        parentActivity.superOnBackPressed()
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
            binding.paintView.photo = originalBitmap
            binding.imageButtonCancelPhoto.setOnClickListener { onCancelPressed() }
            binding.imageButtonAcceptPhoto.setOnClickListener {
                replaceOriginalPhotoWithPhotoWithNotes()
                parentActivity.goCreateProductFragment(filePath!!)
            }
        }.root
    }

    fun replaceOriginalPhotoWithPhotoWithNotes(){
        FileService.createFileFromBitmap(binding.paintView.getBitmap(), parentActivity, filePath)
    }

}