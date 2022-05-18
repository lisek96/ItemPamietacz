package rafalwojcik.prm

import android.Manifest
import android.graphics.ImageDecoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import rafalwojcik.prm.databinding.TakePhotoWithCameraxFragmentBinding
import java.io.File
import java.util.*

class TakePhotoWithCameraXFragment : Fragment() {
    private lateinit var binding: TakePhotoWithCameraxFragmentBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture
    private lateinit var parentActivity : MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        parentActivity = requireActivity() as MainActivity
        return TakePhotoWithCameraxFragmentBinding.inflate(
            inflater, container, false
        ).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForCameraPermission()
        cameraProviderFuture = ProcessCameraProvider.getInstance(parentActivity)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(parentActivity))
        binding.imageButtonAddPhoto.setOnClickListener {
            onImageCapture()
        }

    }

    private fun askForCameraPermission(){
        ActivityCompat.requestPermissions(parentActivity,
            arrayOf(Manifest.permission.CAMERA),
            200
        );
    }

    fun bindPreview(cameraProvider: ProcessCameraProvider) {
        var preview: Preview = Preview.Builder()
            .build()

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .build()

        imageCapture = ImageCapture.Builder()
            .build()
        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageCapture,
            preview
        )

    }

    fun onImageCapture() {
        val path = """${parentActivity.filesDir}${UUID.randomUUID()}.jpg"""
        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(
                File(path)
            ).build()
        imageCapture.takePicture(outputFileOptions, parentActivity.mainExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                }

                override fun onImageSaved(photoFile: ImageCapture.OutputFileResults) {
                    var file = File(path)
                    var source = ImageDecoder.createSource(file)
                    var bitmap = ImageDecoder.decodeBitmap(source)
                }
            })
    }
}