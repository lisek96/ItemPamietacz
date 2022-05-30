package rafalwojcik.prm.fragments

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.CreateProductBinding
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.FileService
import rafalwojcik.prm.service.GeolocationService
import rafalwojcik.prm.service.LocationService2
import java.io.File
import kotlin.concurrent.thread

class CreateProductFragment : Fragment() {

    private lateinit var binding : CreateProductBinding
    private lateinit var parentActivity : MainActivity
    private lateinit var product: Product

    fun setProduct(product: Product) : CreateProductFragment{
        this.product = product
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentActivity = activity as MainActivity
        return CreateProductBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
            if(product.latitude == 0.0 || product.longitude == 0.0){
                provideLocation()
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logo.setImageBitmap(FileService.getBitmapFromFile(File(product.filePath)))
        binding.add.setOnClickListener{
            addProduct()
            parentActivity.goMainFragment()
            Toast.makeText(context, "Product added!",
                Toast.LENGTH_SHORT).show();
        }
        binding.createPickFromMapButton.setOnClickListener {
            parentActivity.goMapFragment(product, PickPlaceFragment.Mode.NEW)
        }
        binding.address.text = product.productAddress
        binding.name.text = Editable.Factory.getInstance().newEditable(product.productName)
    }

    fun addProduct(){
        var product = Product(product.filePath, binding.name.text.toString(), binding.address.text.toString()).apply {
            longitude = product.longitude
            latitude = product.latitude
        }
        parentActivity.addProduct(product)
    }

    private fun provideLocation(){
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            202)

        LocationService2(::myLocationCallback).getLocation(context as Activity)
    }

    private fun myLocationCallback(location : Location){
        println("""Location $location""")
        product.longitude = location.longitude
        product.latitude = location.latitude
        thread {
            binding.address.text = GeolocationService()
                .getAddressFromPosition(requireContext(), LatLng(product.latitude, product.longitude))
        }
    }
}