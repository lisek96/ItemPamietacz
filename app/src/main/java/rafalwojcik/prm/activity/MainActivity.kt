package rafalwojcik.prm.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.launch
import rafalwojcik.prm.R
import rafalwojcik.prm.androidService.LocationService
import rafalwojcik.prm.databinding.ActivityMainBinding
import rafalwojcik.prm.fragments.*
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.LocationService2
import java.io.File


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var notesOnPhotoFragment = NotesOnPhotoFragment()
    private var takePhotoWithCameraXFragment  = TakePhotoWithCameraXFragment()
    private var createProductFragment  : CreateProductFragment? = null
    private var mainFragment  = MainFragment()
    private var galleryFragment  = GalleryFragment()
    private var pickPlaceFragment = PickPlaceFragment()
    private var productDetailFragment = ProductDetailFragment()

    private var locationRequestCode = 202

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        goMainFragment()
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                locationRequestCode)
        }else{
            println("OK, starting foreground service")
            Intent(this, LocationService::class.java).also { intent ->
                startForegroundService(intent)
            }
        }
    }


    fun goMapFragment(product: Product, mode: PickPlaceFragment.Mode) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                pickPlaceFragment.setProduct(product).setMode(mode),
                pickPlaceFragment.javaClass.name
            )
            .addToBackStack(pickPlaceFragment.javaClass.name)
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

    fun goMainFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, mainFragment, mainFragment.javaClass.name)
            .commit()
    }

    fun goTakePhoto(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, takePhotoWithCameraXFragment, takePhotoWithCameraXFragment.javaClass.name)
            .addToBackStack(takePhotoWithCameraXFragment.javaClass.name)
            .commit()
    }

    fun goOpenGallery(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, galleryFragment, galleryFragment.javaClass.name)
            .addToBackStack(galleryFragment.javaClass.name)
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
            .addToBackStack(notesOnPhotoFragment.javaClass.name)
            .commit()
    }

    fun goCreateProductFragment(newProduct: Product){
        if(createProductFragment == null){
            createProductFragment = CreateProductFragment().setProduct(newProduct)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, createProductFragment!!
                .setProduct(newProduct), createProductFragment!!.javaClass.name)
            .addToBackStack(createProductFragment!!.javaClass.name)
            .commit()
    }

    fun goDetails(product: Product){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, productDetailFragment.setProduct(product), productDetailFragment.javaClass.name)
            .addToBackStack(productDetailFragment.javaClass.name)
            .commit()
    }

    fun addProduct(product: Product){
        mainFragment.addProduct(product)
    }

    fun updateProduct(product: Product){
        mainFragment.updateProduct(product)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == locationRequestCode || grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startForegroundService()
        }
    }

    private fun startForegroundService(){
        println("OK, starting foreground service")
        Intent(this, LocationService::class.java).also { intent ->
            startForegroundService(intent)
        }
    }

}