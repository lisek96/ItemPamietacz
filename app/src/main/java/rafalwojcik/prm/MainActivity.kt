package rafalwojcik.prm

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import rafalwojcik.prm.databinding.ActivityMainBinding

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, MainFragment())
            .commit();
    }

    fun goTakePhoto(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, TakePhotoWithCameraXFragment())
            .commit()
    }

    fun goTakeNoteOnPhoto(bitmap: Bitmap){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, NotesOnPhotoFragment(bitmap))
            .commit()
    }
}