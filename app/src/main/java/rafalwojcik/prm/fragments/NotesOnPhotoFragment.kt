package rafalwojcik.prm.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import rafalwojcik.prm.service.FileService
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.database.AppDatabase
import rafalwojcik.prm.database.DatabaseGiver
import rafalwojcik.prm.databinding.NotesOnPhotoFragmentBinding
import rafalwojcik.prm.model.Product
import java.io.File
import kotlin.concurrent.thread

class NotesOnPhotoFragment() : Fragment() {
    private lateinit var binding: NotesOnPhotoFragmentBinding
    private  var filePath : String? = null
    private lateinit var bitMap : Bitmap
    private lateinit var parentActivity : MainActivity

    fun prepareBitMap(filePath: String): NotesOnPhotoFragment {
        this.filePath = filePath
        bitMap = FileService.getBitmapFromFile(filePath)
        return this
    }

    fun onCancelPressed(){
        File(filePath)?.delete()
        filePath = null
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
            binding.paintView.photo = bitMap
            binding.imageButtonCancelPhoto.setOnClickListener { onCancelPressed() }
            binding.imageButtonAcceptPhoto.setOnClickListener {  addProduct()  }
        }.root
    }

    fun addProduct(){
        val productDao = DatabaseGiver.getDb(parentActivity).productDao()
       thread {
           productDao.insert(
               Product(
                   "xx", "xx", 34.0, 35.0,
                   "xx", "xx", "xx"
               )
           )
       }
        thread{
            println(productDao.getAll())
        }
    }
}