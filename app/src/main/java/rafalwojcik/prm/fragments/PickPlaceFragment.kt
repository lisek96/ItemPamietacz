package rafalwojcik.prm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import rafalwojcik.prm.R
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.databinding.PickPlaceFragmentBinding
import rafalwojcik.prm.model.Product
import rafalwojcik.prm.service.GeolocationService
import rafalwojcik.prm.service.LocationService2


class PickPlaceFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: PickPlaceFragmentBinding
    private lateinit var parentActivity : MainActivity
    private var mapView: MapView? = null
    private lateinit var googleMap: GoogleMap
    private var product = Product("", "", "")
    private lateinit var currentMarker : Marker
    private lateinit var mode : Mode
    private var currentZoom = 15;

    enum class Mode {
        NEW, EDIT
    }

    fun setProduct(product: Product) : PickPlaceFragment{
        this.product = product
        return this
    }

    fun setMode(mode : Mode) : PickPlaceFragment{
        this.mode = mode
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        parentActivity = activity as MainActivity
        return PickPlaceFragmentBinding.inflate(
            inflater, container, false
        ).apply {
            binding = this
            binding.textView.text = product.productAddress
            binding.buttonApply.setOnClickListener{ apply() }
            binding.localizeButton.setOnClickListener {
                LocationService2({ location -> moveToNewMarker(LatLng(location.latitude, location.longitude)) }).getLocation(parentActivity)
            }
            binding.zoomIN.setOnClickListener {
                var zoom = currentZoom++.toFloat()
                zoomCamera(zoom)
            }
            binding.zoomOUT.setOnClickListener {
                var zoom = currentZoom--.toFloat()
               zoomCamera(zoom)
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map) as MapView
        mapView!!.onCreate(savedInstanceState);
        mapView!!.onResume();
        mapView!!.getMapAsync(this)

    }

    override fun onMapReady(mMap: GoogleMap) {
        googleMap = mMap
        addInitMarker(LatLng(product.latitude, product.longitude))
    }

    private fun addInitMarker(latLng: LatLng){
        currentMarker = googleMap.addMarker(MarkerOptions()
            .position(LatLng(latLng.latitude, latLng.longitude)))
        moveToNewMarker(currentMarker.position)
        googleMap.setOnMapClickListener { latLng-> moveToNewMarker(latLng) }
    }

    private fun moveToNewMarker(latLng: LatLng){
        val newPosition = LatLng(latLng.latitude, latLng.longitude)
        var newMarker = googleMap.addMarker(MarkerOptions().position(newPosition))
        val cameraPosition = CameraPosition.Builder()
            .target(newPosition).zoom(currentZoom.toFloat()).build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        currentMarker.remove()
        currentMarker = newMarker
        updateInfo(newPosition)
    }

    private fun updateInfo(newPosition : LatLng){
        product.productAddress = GeolocationService().getAddressFromPosition(requireContext(), newPosition)
        product.latitude = newPosition.latitude
        product.longitude = newPosition.longitude
        binding.textView.text = product.productAddress
    }

    private fun apply(){
        if(mode == Mode.NEW){
            parentActivity.goCreateProductFragment(product)
        }else if(mode == Mode.EDIT){
            parentActivity.goDetails(product)
        }
    }

    private fun zoomCamera(zoom: Float){
        val cameraPosition = CameraPosition.Builder().target(LatLng(product.latitude, product.longitude)).zoom(zoom).build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}