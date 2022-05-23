package rafalwojcik.prm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.launch
import rafalwojcik.prm.R
import rafalwojcik.prm.databinding.ActivityMainBinding
import rafalwojcik.prm.fragments.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var notesOnPhotoFragment : NotesOnPhotoFragment = NotesOnPhotoFragment()
    private var takePhotoWithCameraXFragment : TakePhotoWithCameraXFragment = TakePhotoWithCameraXFragment()
    private var createProductFragment : CreateProductFragment = CreateProductFragment()
    private var galleryFragment : GalleryFragment = GalleryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment(), MainFragment().javaClass.name)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager
                .findFragmentByTag(notesOnPhotoFragment.javaClass.name)?.isVisible == true){
                    lifecycle.coroutineScope.launch{
                        notesOnPhotoFragment.onCancelPressed()
                    }
        }
        else {
            superOnBackPressed()
        }

    }

    fun superOnBackPressed(){
        super.onBackPressed()
    }

    fun goTakePhoto(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, takePhotoWithCameraXFragment, TakePhotoWithCameraXFragment().javaClass.name)
            .addToBackStack(TakePhotoWithCameraXFragment().javaClass.name)
            .commit()
    }

    fun goOpenGallery(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, galleryFragment, GalleryFragment().javaClass.name)
            .addToBackStack(TakePhotoWithCameraXFragment().javaClass.name)
            .commit()
    }

    fun goTakeNoteOnPhoto(filePath: String){
        notesOnPhotoFragment.prepareBitMap(filePath)
        goTakeNoteOnPhoto()
    }

    fun goTakeNoteOnPhoto(file: File){
        notesOnPhotoFragment.prepareBitMap(file)
        goTakeNoteOnPhoto()
    }

    private fun goTakeNoteOnPhoto(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, notesOnPhotoFragment!!)
            .addToBackStack(NotesOnPhotoFragment().javaClass.name)
            .commit()
    }

    fun goCreateProductFragment(filePath: String){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, createProductFragment.setFilePath((filePath)), createProductFragment.javaClass.name)
            .addToBackStack(NotesOnPhotoFragment().javaClass.name)
            .commit()
    }
}