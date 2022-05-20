package rafalwojcik.prm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.launch
import rafalwojcik.prm.fragments.MainFragment
import rafalwojcik.prm.fragments.NotesOnPhotoFragment
import rafalwojcik.prm.R
import rafalwojcik.prm.fragments.TakePhotoWithCameraXFragment
import rafalwojcik.prm.databinding.ActivityMainBinding
import rafalwojcik.prm.fragments.GalleryFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var notesOnPhotoFragment : NotesOnPhotoFragment = NotesOnPhotoFragment()
    private var takePhotoWithCameraXFragment : TakePhotoWithCameraXFragment = TakePhotoWithCameraXFragment()
    private var galleryFragment : GalleryFragment = GalleryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment(), MainFragment().javaClass.name)
            .addToBackStack(MainFragment().javaClass.name)
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
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, notesOnPhotoFragment!!)
            .addToBackStack(NotesOnPhotoFragment().javaClass.name)
            .commit()
    }
}