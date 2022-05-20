package rafalwojcik.prm.Service

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.viewbinding.ViewBinding
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.launch
import rafalwojcik.prm.MainActivity
import rafalwojcik.prm.TakePhotoWithCameraXFragment
import rafalwojcik.prm.databinding.TakePhotoWithCameraxFragmentBinding
import java.io.File
import java.util.*

class CameraService(_binding : TakePhotoWithCameraxFragmentBinding,
                    _parentActivity : MainActivity,
                    _parentFragment : TakePhotoWithCameraXFragment) {

    private var binding = _binding
    private var parentActivity = _parentActivity
    private var parentFragment = _parentFragment
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
        ProcessCameraProvider.getInstance(parentActivity)
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraProvider : ProcessCameraProvider

    fun startCamera() : CameraService{
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            bindPreview()
        }, ContextCompat.getMainExecutor(parentActivity))
        return this
    }

    fun bindPreview() {
        var preview: Preview = Preview.Builder()
            .build()

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .build()

        imageCapture = ImageCapture.Builder()
            .build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            parentActivity as LifecycleOwner,
            cameraSelector,
            imageCapture,
            preview
        )

    }

    fun onImageCapture(){
        var currentFilePath = """${parentActivity.filesDir}${UUID.randomUUID()}.jpg"""
        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(
                File(currentFilePath)
            ).build()
        imageCapture.takePicture(outputFileOptions, parentActivity.mainExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                }

                override fun onImageSaved(photoFile: ImageCapture.OutputFileResults) {
                    cameraProvider.unbindAll()
                    reportImageSaved(currentFilePath)
                }
            })
    }

    fun reportImageSaved(filePath : String){
        parentFragment.goTakeNotesOnPhoto(filePath)
    }
}