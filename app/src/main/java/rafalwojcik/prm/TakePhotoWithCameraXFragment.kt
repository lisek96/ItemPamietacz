package rafalwojcik.prm

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch
import rafalwojcik.prm.Service.CameraService
import rafalwojcik.prm.databinding.TakePhotoWithCameraxFragmentBinding
import java.io.File
import java.util.*

class TakePhotoWithCameraXFragment : Fragment() {
    private lateinit var binding: TakePhotoWithCameraxFragmentBinding
    private lateinit var parentActivity : MainActivity
    private lateinit var cameraService: CameraService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = requireActivity() as MainActivity
        return TakePhotoWithCameraxFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
            binding.imageButtonAddPhoto.setOnClickListener {
                cameraService.onImageCapture()
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraService = CameraService(binding, parentActivity, this).startCamera()
    }

    fun goTakeNotesOnPhoto(filePath : String){
        parentActivity.goTakeNoteOnPhoto(filePath)
    }
}