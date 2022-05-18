package rafalwojcik.prm

import android.Manifest
import android.graphics.ImageDecoder
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
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            200
        );
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
        println(filesDir)
        binding.button.setOnClickListener {
            onClick()
        }
    }

    fun bindPreview(cameraProvider: ProcessCameraProvider) {
        var preview: Preview = Preview.Builder()
            .build()

        var cameraSelector: CameraSelector = CameraSelector.Builder()
            .build()

        imageCapture = ImageCapture.Builder()
            .build()
        preview.setSurfaceProvider(binding.previewView.surfaceProvider)

        var camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            imageCapture,
            preview
        )

    }

    fun onClick() {
        val path = """$filesDir${UUID.randomUUID()}.jpg"""
        val outputFileOptions =
            ImageCapture.OutputFileOptions.Builder(
                File(path)
            ).build()
        imageCapture.takePicture(outputFileOptions, mainExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(error: ImageCaptureException) {
                }

                override fun onImageSaved(photoFile: ImageCapture.OutputFileResults) {
                    var file = File(path)
                    var source = ImageDecoder.createSource(file)
                    var bitmap = ImageDecoder.decodeBitmap(source);
                    binding.imageView.setImageBitmap(bitmap);
                }
            })
    }

    private fun test(uri: Uri?) {

    }
}