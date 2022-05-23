package rafalwojcik.prm.service

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

class GeolocationService {
    companion object{
        fun getAddressFromPosition(context: Context, position : LatLng) : String {
            return Geocoder(context)
                .getFromLocation(position.latitude, position.longitude, 1)[0]
                .getAddressLine(0)
        }
    }
}