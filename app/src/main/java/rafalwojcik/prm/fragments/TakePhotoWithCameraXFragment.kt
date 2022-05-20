package rafalwojcik.prm.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import rafalwojcik.prm.service.CameraService
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.TakePhotoWithCameraxFragmentBinding
import kotlin.concurrent.thread

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
        thread {
            cameraService = CameraService(binding, parentActivity, this).startCamera()
        }
    }

    fun goTakeNotesOnPhoto(filePath : String){
        parentActivity.goTakeNoteOnPhoto(filePath)
    }
}